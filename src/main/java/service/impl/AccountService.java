package service.impl;

import classes.Money;
import controller.dto.accounts.OperationDto;
import enums.Status;
import model.*;
import repository.*;
import service.interfaces.IAccountService;

import jdk.dynalink.Operation;
import model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private CheckingRepository checkingRepository;

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private Money balance;

    public model.Operation transfer(OperationDto operationDto, Principal principal) {

        if (principal==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "LOGIN");
        }

        Optional<Account> originAccount = accountRepository.findById(operationDto.getOriginAccountId());

        Optional<Account> destinationAccount = accountRepository.findById(operationDto.getDestinationAccountId());

        if (!originAccount.isPresent() || !destinationAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NOT FOUND ORIGIN ACCOUNT");
        }

        Account accountOrigin = originAccount.get();
        Account accountDestination = destinationAccount.get();
        String username = principal.getName();

        if (!hasPermission(accountOrigin, username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT PERMITTED");
        }

        if (!checkDestinationNameIsDestinationOwner(operationDto, destinationAccount.get())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NOT FOUND DESTINATION ACCOUNT");
        }

        if (checkFreeze(accountOrigin) || checkFreeze(accountDestination)){
            throw new ResponseStatusException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "ACCOUNT FROZEN");
        }

        accountOrigin.getBalance().decreaseAmount(operationDto.getAmount());
        accountDestination.getBalance().increaseAmount(operationDto.getAmount());

        if (checkPenaltyFee(accountOrigin)) {
            accountOrigin.getBalance().decreaseAmount(accountOrigin.getPenaltyFee());
            if (accountOrigin instanceof Savings) {
                ((Savings) accountOrigin).setBelowMinimumBalance(true);}
        }
        if (accountOrigin instanceof Checking) {
            ((Checking) accountOrigin).setBelowMinimumBalance(true);
        }

        if (accountDestination instanceof Checking) {
            if (accountDestination.getBalance().getAmount().compareTo(((Checking) accountDestination).getMinimumBalance().getAmount())>0 &&
                    ((Checking) accountDestination).getBelowMinimumBalance()){
                ((Checking) accountDestination).setBelowMinimumBalance(false);
            }
        }

        accountRepository.save(accountOrigin);
        accountRepository.save(accountDestination);

        //PREGUNTAR FALLO EN MONEY ME LO PILLA COMO BIGDECIMAL
        //Operation operation = new Operation(accountOrigin, accountDestination, new Money(operationDto.getAmount()), operationDto.getName());
        //return operationRepository.save(operation);
return null; //CAMBIAR ES SOLO PARA QUE NO DE FALLO
    }

    private boolean checkFraud(Account accountOrigin, BigDecimal amount) {
        return false;
    }

    private void getBelowMinimumBalance() {

    }

    @Override
    public model.Operation transferToThirdParty(String hashKey, String secretKey, OperationDto operationDto) {
        return null;
    }

    @Override
    public model.Operation transferFromThirdParty(String hashKey, String secretKey, OperationDto operationDto) {
        return null;
    }

    public Boolean checkFreeze(Account account){

        if (account.getStatus().equals(Status.FROZEN)){
            return true;
        }
        return false;
    }

    private BigDecimal CalculateInterest(BigDecimal amount, BigDecimal interest, int iterations){

        BigDecimal result = amount;
        for (int i=0; i<iterations; i++) {
            result = result.multiply(interest.add(new BigDecimal("1")));
        }
        return result;
    }

    public Boolean hasPermissionOrAdmin(Account account, String username) {
        Optional <User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            if (adminRepository.findById(user.get().getId()).isPresent()){
                return true;
            }
        }
        if (accountHolderRepository.findById(user.get().getId()).isPresent()){
            if (account.getPrimaryOwner().getUsername().equals(username)) {
                return true;
            } else if (account.getSecondaryOwner()!=null) {
                if (account.getSecondaryOwner().getUsername().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean hasPermission(Account account, String username) {
        User user = userRepository.findByUsername(username).get();
        if (accountHolderRepository.findById(user.getId()).isPresent()){
            if (account.getPrimaryOwner().getUsername().equals(username)) {
                return true;
            } else if (account.getSecondaryOwner()!=null) {
                if (account.getSecondaryOwner().getUsername().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean checkDestinationNameIsDestinationOwner(OperationDto operationDto, Account destinationAccount) {
        if (operationDto.getName().equals(destinationAccount.getPrimaryOwner().getName())) {
            return true;
        } else if (destinationAccount.getSecondaryOwner() != null) {
            if (operationDto.getName().equals(destinationAccount.getSecondaryOwner().getName())){
                return true;
            }
        }
        return false;
    }

    public Boolean checkPenaltyFee(Account originAccount) {
        if (originAccount instanceof Savings) {
            if (originAccount.getBalance().getAmount().compareTo(((Savings) originAccount).getMinimumBalance().getAmount()) < 0  &&
                    !((Savings) originAccount).getBelowMinimumBalance()) {
                return true;
            }
        } else if (originAccount instanceof Checking) {
            if (originAccount.getBalance().getAmount().compareTo(((Checking) originAccount).getMinimumBalance().getAmount()) < 0 &&
                    !((Checking) originAccount).getBelowMinimumBalance() ) {
                return true;
            }
        }
        return false;
    }

    public Boolean checkSecretKey(Account account, String secretKey){

        if (account instanceof CreditCard) {
            return true;
        } else if (account instanceof Savings){
            return passwordEncoder.matches(secretKey, ((Savings) account).getSecretKey());
        } else if (account instanceof Checking) {
            return passwordEncoder.matches(secretKey, ((Checking) account).getSecretKey());
        } else if (account instanceof StudentChecking){
            return passwordEncoder.matches(secretKey, ((StudentChecking) account).getSecretKey());
        }
        return false;
    }

    public Money checkBalance(Long id, Principal principal) {
        return balance;
    }
}

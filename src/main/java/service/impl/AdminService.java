package service.impl;

import classes.Money;
import controller.dto.accounts.MoneyDto;
import controller.dto.users.AdminDto;
import controller.dto.users.ThirdPartyDto;
import enums.Role;
import model.*;
import repository.AccountRepository;
import repository.AdminRepository;
import repository.RolesRepository;
import repository.ThirdPartyRepository;
import service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    public RolesRepository rolesRepository;
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Admin createAdmin(AdminDto adminDto) {
        Admin admin = new Admin(adminDto.getName(), adminDto.getUsername(), adminDto.getPassword());
        adminRepository.save(admin);
        rolesRepository.save(new Roles(Role.ADMIN, admin));
        return admin;
    }

    public ThirdParty createThirdParty(ThirdPartyDto thirdPartyDto) {
        ThirdParty thirdParty = new ThirdParty(thirdPartyDto.getName(), thirdPartyDto.getHashKey());
        thirdPartyRepository.save(thirdParty);
        return thirdParty;
    }

    public Account modifyBalance(Long id, MoneyDto modifiedBalance){
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()){
            account.get().setBalance(new Money(modifiedBalance.getAmount()));

            if (account.get() instanceof Checking){
                if (account.get().getBalance().getAmount().compareTo(((Checking) account.get()).getMinimumBalance().getAmount())>=0){
                    ((Checking) account.get()).setBelowMinimumBalance(false);
                } else {
                    ((Checking) account.get()).setBelowMinimumBalance(true);
                }
            } else if (account.get() instanceof Savings){
                if (account.get().getBalance().getAmount().compareTo(((Savings) account.get()).getMinimumBalance().getAmount())>=0){
                    ((Savings) account.get()).setBelowMinimumBalance(false);
                } else {
                    ((Savings) account.get()).setBelowMinimumBalance(true);
                }
            }

            return accountRepository.save(account.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

}

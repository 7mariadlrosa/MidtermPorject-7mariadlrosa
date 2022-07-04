package service.impl;

import classes.Money;
import classes.Utils;
import controller.dto.accounts.SavingsDto;
import model.AccountHolder;
import model.Savings;

import repository.AccountHolderRepository;
import repository.SavingsRepository;
import service.interfaces.ISavingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SavingsService implements ISavingsService {

    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;


    /** Method to create new Savings Account **/
    public Savings create(SavingsDto savingsDto) {
        Savings savings;
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(savingsDto.getPrimaryOwnerId());
        if (accountHolder.isPresent()) {
            savings = new Savings(accountHolder.get(),
                    new Money(savingsDto.getBalance()),
                    savingsDto.getSecretKey());
            if (savingsDto.getSecondaryOwnerId() != null){
                Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(savingsDto.getSecondaryOwnerId());
                if (secondaryOwner.isPresent()){
                    savings.setSecondaryOwner(secondaryOwner.get());
                }
            }
            if (savingsDto.getMinimumBalance()!=null) {
                savings.setMinimumBalance(new Money(savingsDto.getMinimumBalance()));
            }
            if (savingsDto.getInterestRate()!=null) {
                savings.setInterestRate(savingsDto.getInterestRate());
            }
            return savingsRepository.save(savings);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found");
        }
    }


    /** Method to find all savings accounts (you have to be and ADMIN) **/
    public List<Savings> showAll(){
        if (savingsRepository.findAll().size()==0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No checking accounts found");
        }
        return savingsRepository.findAll();
    }

    /** Method to find one savings account by id (you have to be and ADMIN) **/
    public Optional<Savings> find(Long id){
        if (savingsRepository.findById(id).isPresent()){
            return savingsRepository.findById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no account with the provided id");
        }
    }



}

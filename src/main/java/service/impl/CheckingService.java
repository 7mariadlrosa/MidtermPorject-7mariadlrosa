package service.impl;

import classes.Money;
import controller.dto.accounts.CheckingDto;
import model.Account;
import classes.Utils;

import model.AccountHolder;
import model.Checking;
import model.StudentChecking;

import repository.AccountHolderRepository;
import repository.CheckingRepository;
import repository.StudentCheckingRepository;
import service.interfaces.ICheckingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CheckingService implements ICheckingService {

    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    public Account create(CheckingDto checkingDto) {
        Checking checking;
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(checkingDto.getPrimaryOwnerId());
        if (accountHolder.isPresent()){
            if(Utils.calculateYears(accountHolder.get().getDateOfBirth())<24){
                StudentChecking studentChecking = new StudentChecking(
                        accountHolder.get(),
                        new Money(checkingDto.getBalance()),
                        checkingDto.getSecretKey());
                if (checkingDto.getSecondaryOwnerId() != null){
                    Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(checkingDto.getSecondaryOwnerId());
                    if (secondaryOwner.isPresent()){
                        studentChecking.setSecondaryOwner(secondaryOwner.get());
                    }
                }
                return studentCheckingRepository.save(studentChecking);
            } else {
                checking = new Checking (accountHolder.get(),
                        new Money(checkingDto.getBalance()),
                        checkingDto.getSecretKey());
                if (checkingDto.getSecondaryOwnerId() != null){
                    Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(checkingDto.getSecondaryOwnerId());
                    if (secondaryOwner.isPresent()){
                        checking.setSecondaryOwner(secondaryOwner.get());
                    }
                }
                return checkingRepository.save(checking);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found");
        }
    }

    public List<Checking> showAll(){
        if (checkingRepository.findAll().size()==0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND");
        }
        return checkingRepository.findAll();
    }

    public Optional<Checking> find(Long id){
        if (checkingRepository.findById(id).isPresent()){
            return checkingRepository.findById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND ACCOUNT WITH THE SAME ID");
        }
    }
}

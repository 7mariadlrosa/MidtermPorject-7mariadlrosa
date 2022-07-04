package service.impl;

import classes.Money;
import controller.dto.accounts.CreditCardDto;
import model.AccountHolder;
import model.CreditCard;

import repository.AccountHolderRepository;
import repository.CreditCardRepository;
import service.interfaces.ICreditCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService implements ICreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    /** Method to create new Credit Card Accounts **/
    public CreditCard create(CreditCardDto creditCardDto) {
        CreditCard creditCard;
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(creditCardDto.getPrimaryOwnerId());
        if(accountHolder.isPresent()){
            creditCard = new CreditCard(accountHolder.get(),
                    new Money(creditCardDto.getBalance()));
            if (creditCardDto.getSecondaryOwnerId()!=null){
                Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(creditCardDto.getSecondaryOwnerId());
                if (secondaryOwner.isPresent()){
                    creditCard.setSecondaryOwner(secondaryOwner.get());
                }
            }
            if (creditCardDto.getCreditLimit()!=null){
                creditCard.setCreditLimit(new Money(creditCardDto.getCreditLimit()));
            }
            if (creditCardDto.getInterestRate()!=null){
                creditCard.setInterestRate(creditCardDto.getInterestRate());
            }
            return creditCardRepository.save(creditCard);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Primary owner not found");
        }
    }

    /** Method to find all credit card accounts (you have to be and ADMIN) **/
    public List<CreditCard> showAll(){
        if (creditCardRepository.findAll().size()==0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No checking accounts found");
        }
        return creditCardRepository.findAll();
    }

    /** Method to find one credit card account by id (you have to be and ADMIN) **/
    public Optional<CreditCard> find(Long id){
        if (creditCardRepository.findById(id).isPresent()){
            return creditCardRepository.findById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no account with the provided id");
        }
    }
}
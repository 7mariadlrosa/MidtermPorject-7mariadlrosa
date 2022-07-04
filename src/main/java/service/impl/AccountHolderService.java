package service.impl;

import classes.Address;
import controller.dto.users.AccountHolderDto;
import enums.Role;
import model.AccountHolder;

import model.Roles;
import repository.AccountHolderRepository;
import repository.RolesRepository;
import service.interfaces.IAccountHolderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
@Service
public class AccountHolderService implements IAccountHolderService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;


    @Autowired
    private RolesRepository rolesRepository;

    public AccountHolder create(AccountHolderDto accountHolderDto) {
        AccountHolder accountHolder = new AccountHolder(accountHolderDto.getName(),
                accountHolderDto.getUsername(),
                accountHolderDto.getPassword(),
                accountHolderDto.getDateOfBirth(),
                new Address(accountHolderDto.getPrimaryAddress()));

        if (accountHolderDto.getMailingAddress()!=null) {
            accountHolder.setMailingAddress(String.valueOf(new Address(accountHolderDto.getMailingAddress())));
        }
        accountHolderRepository.save(accountHolder);
        rolesRepository.save(new Role(Role.ACCOUNT_HOLDER, accountHolder));
        return accountHolder; //NO ENTIENDO PORQUÉ DA FALLO
    }
}

*/
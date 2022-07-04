package controller.impl;

import controller.dto.users.AccountHolderDto;
import controller.interfaces.IAccountHolderController;
import model.AccountHolder;
import service.interfaces.IAccountHolderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    private IAccountHolderService accountHolderService;

    @PostMapping("/admin/create-account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder create(@RequestBody @Valid AccountHolderDto accountHolderDto){
        return accountHolderService.create(accountHolderDto);
    }

}

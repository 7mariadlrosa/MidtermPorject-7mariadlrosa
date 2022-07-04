package controller.impl;

import classes.Money;
import controller.dto.accounts.OperationDto;
import controller.interfaces.IAccountController;
import model.Operation;
import service.impl.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/check-balance/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Money checkBalance(@PathVariable("accountId") Long id, Principal principal) {
        return accountService.checkBalance(id, principal);
    }

    @PatchMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public Operation transfer(@RequestBody @Valid OperationDto operationDto, Principal principal){
        return (Operation) accountService.transfer(operationDto, principal);
    }
}

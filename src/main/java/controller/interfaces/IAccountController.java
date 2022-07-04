package controller.interfaces;

import classes.Money;
import controller.dto.accounts.OperationDto;
import model.Operation;

import java.security.Principal;

public interface IAccountController {

    public Money checkBalance(Long id, Principal principal);

    public Operation transfer(OperationDto operationDto, Principal principal);

}

package controller.interfaces;

import controller.dto.users.AccountHolderDto;
import model.AccountHolder;

public interface IAccountHolderController {

    public AccountHolder create(AccountHolderDto accountHolderDto);
}
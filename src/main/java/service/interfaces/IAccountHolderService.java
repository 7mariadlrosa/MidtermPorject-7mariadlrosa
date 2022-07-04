package service.interfaces;

import controller.dto.users.AccountHolderDto;
import model.AccountHolder;

public interface IAccountHolderService {

    public AccountHolder create(AccountHolderDto accountHolderDto);
}

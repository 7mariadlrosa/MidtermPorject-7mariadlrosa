package controller.interfaces;

import classes.Money;
import controller.dto.accounts.MoneyDto;
import controller.dto.users.AdminDto;
import controller.dto.users.ThirdPartyDto;
import model.Account;
import model.Admin;
import model.ThirdParty;

public interface IAdminController {

    public Admin createAdmin(AdminDto adminDto);

    public ThirdParty createThirdParty(ThirdPartyDto thirdPartyDto);

    public Account modifyBalance(Long id, MoneyDto modifiedBalance);

}

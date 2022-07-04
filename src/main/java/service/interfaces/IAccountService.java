package service.interfaces;

import classes.Money;
import controller.dto.accounts.OperationDto;
import model.Operation;

import java.security.Principal;

public interface IAccountService {

    /** method to check any account balance **/
    public Money checkBalance(Long id, Principal principal);

    /** Route to make transfers between accounts in database **/
    public Operation transfer(OperationDto operationDto, Principal principal);

    /** Route to make transfers to third-party accounts from normal accounts **/
    public Operation transferToThirdParty(String hashKey, String secretKey, OperationDto operationDto);

    /** Route to make transfers from third-party accounts to normal accounts **/
    public Operation transferFromThirdParty(String hashKey, String secretKey, OperationDto operationDto);
}

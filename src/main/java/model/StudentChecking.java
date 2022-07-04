package model;

import classes.Money;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class StudentChecking extends Account{

    private String secretKey;

    public StudentChecking() {
    }

    @Override
    public void setStatus(enums.Status Status) {

    }

    public StudentChecking(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, String secretKey) {
        super(primaryOwner, secondaryOwner, balance);
        setSecretKey(secretKey);
    }

    public StudentChecking(AccountHolder primaryOwner, Money balance, String secretKey) {
        super(primaryOwner, balance);
        setSecretKey(secretKey);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}

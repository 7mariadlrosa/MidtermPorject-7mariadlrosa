package model;

import classes.Money;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Savings extends Account{

    private String secretKey;

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    })
    private Money minimumBalance;

    @Column(columnDefinition = "DECIMAL")
    private BigDecimal interestRate;

    private Boolean belowMinimumBalance;

    public Savings() {
    }

    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, String secretKey) {
        super(primaryOwner, secondaryOwner, balance);
        setSecretKey(secretKey);
        setDefaultMinimumBalance();
        setDefaultInterestRate();
        setBelowMinimumBalance(false);
    }

    public Savings(AccountHolder primaryOwner, Money balance, String secretKey) {
        super(primaryOwner, balance);
        setSecretKey(secretKey);
        setDefaultMinimumBalance();
        setDefaultInterestRate();
        setBelowMinimumBalance(false);
    }

    public Savings(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, String secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner, balance);
        setSecretKey(secretKey);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
        setBelowMinimumBalance(false);
    }

    public Savings(AccountHolder primaryOwner, Money balance, String secretKey, Money minimumBalance, BigDecimal interestRate) {
        super(primaryOwner, balance);
        setSecretKey(secretKey);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
        setBelowMinimumBalance(false);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setDefaultMinimumBalance() {
        this.minimumBalance = new Money(new BigDecimal("1000"));
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setDefaultInterestRate() {
        this.interestRate = new BigDecimal("0.0025");
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Boolean getBelowMinimumBalance() {
        return belowMinimumBalance;
    }

    public void setBelowMinimumBalance(Boolean belowMiniminBalance) {
        this.belowMinimumBalance = belowMiniminBalance;
    }

    @Override
    public void setStatus(enums.Status Status) {

    }

    public void setLastInterestUpdate(LocalDate of) {
    }
}
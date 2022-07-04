package model;

import classes.Money;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@PrimaryKeyJoinColumn(name="id")
public class CreditCard extends Account{

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency"))
    })
    private Money creditLimit;

    @Column(columnDefinition = "DECIMAL(5,4)")
    private BigDecimal interestRate;

    public CreditCard() {
    }

    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance) {
        super(primaryOwner, secondaryOwner, balance);
        setDefaultCreditLimit();
        setDefaultInterestRate();
    }

    public CreditCard(AccountHolder primaryOwner, Money balance) {
        super(primaryOwner, balance);
        setDefaultCreditLimit();
        setDefaultInterestRate();
    }

    @Override
    public void setStatus(enums.Status Status) {

    }

    public CreditCard(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, Money creditLimit, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner, balance);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public CreditCard(AccountHolder primaryOwner, Money balance, Money creditLimit, BigDecimal interestRate) {
        super(primaryOwner, balance);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setDefaultCreditLimit() {
        this.creditLimit = new Money(new BigDecimal("100"));
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setDefaultInterestRate() {
        this.interestRate = new BigDecimal("0.2");
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void setLastInterestUpdate(LocalDate of) {
    }
}
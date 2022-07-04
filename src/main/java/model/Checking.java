package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import classes.Money;
import enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Checking extends Account{

    private String secretKey;

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency"))
    })
    private Money monthlyMaintenanceFee;

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    })
    private Money minimumBalance;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate lasMonthlyMaintenance;

    private Boolean belowMinimumBalance;

    public Checking() {
    }

    public Checking(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance, String secretKey) {
        super(primaryOwner, secondaryOwner, balance);
        setSecretKey(secretKey);
        setDefaultMonthlyMaintenanceFee();
        setDefaultMinimumBalance();
        setBelowMinimumBalance(false);
        setLasMonthlyMaintenance(LocalDate.now());
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public LocalDate getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setDefaultMonthlyMaintenanceFee() {
        this.monthlyMaintenanceFee = new Money(new BigDecimal("12"));
    }

    public void setMonthlyMaintenanceFee(Money monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }


    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setDefaultMinimumBalance() {
        this.minimumBalance = new Money(new BigDecimal("250"));
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    @Override
    public enums.Status getStatus() {
        return Status;
    }

    @Override
    public void setStatus(Status Status) {
        this.Status = Status;
    }

    public Boolean getBelowMinimumBalance() {
        return belowMinimumBalance;
    }

    public void setBelowMinimumBalance(Boolean belowMinimumBalance) {
        this.belowMinimumBalance = belowMinimumBalance;
    }

    public LocalDate getLasMonthlyMaintenance() {
        return lasMonthlyMaintenance;
    }

    public void setLasMonthlyMaintenance(LocalDate lasMonthlyMaintenance) {
        this.lasMonthlyMaintenance = lasMonthlyMaintenance;
    }
}

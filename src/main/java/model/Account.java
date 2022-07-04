package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import classes.Money;
import enums.Status;
import jdk.dynalink.Operation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name= "account_table")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id //PRIMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_account")
    protected Long id;

    @Column(name="pOwner_account")
    @ManyToOne(optional = false)
    protected AccountHolder primaryOwner;

    @Column(name="sOwner_account")
    @ManyToOne
    protected AccountHolder secondaryOwner;

    @Column(name="createdDate_account")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    protected LocalDate createdDate;

    @Embedded
    @Column(name="balance_account")
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
    })
    protected Money balance;

    @Embedded
    @Column(name="penaltyFee_account")
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount")),
            @AttributeOverride(name = "currency", column = @Column(name ="penalty_fee_currency"))
    })
    protected Money penaltyFee;

    @Column(name="monthlyMaintenanceFee_account")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    protected LocalDate monthlyMaintenanceFee;

    @Enumerated(EnumType.STRING)
    @Column(name="penaltyFee_account")
    protected Status Status;

    //TO SEND MONEY (No funciona aún)
    @OneToMany(mappedBy="originAccount")
    @JsonIgnore
    protected List<Operation> sentMoney;

    @JsonIgnore
    @OneToMany(mappedBy="destinationAccount")
    protected List<Operation> receivedMoney;


    public Account() {
    }

    public Account(AccountHolder primaryOwner, AccountHolder secondaryOwner, Money balance) {
        setPrimaryOwner(primaryOwner);
        setSecondaryOwner(secondaryOwner);
        setCreatedDate();
        setMonthlyMaintenanceFee(LocalDate.now());
        setBalance(balance);
        setPenaltyFee();
        setAccountStatus(Status.ACTIVE);
    }

    public Account(AccountHolder primaryOwner, Money balance) {
        setPrimaryOwner(primaryOwner);
        setCreatedDate();
        setMonthlyMaintenanceFee(LocalDate.now());
        setBalance(balance);
        setPenaltyFee();
        setAccountStatus(Status.ACTIVE);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate() {
        this.createdDate = LocalDate.now();
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee() {
        this.penaltyFee = new Money(new BigDecimal("40"));
    }

    public List<Operation> getSentMoney() {
        return sentMoney;
    }

    public void setSentMoney(List<Operation> sentMoney) {
        this.sentMoney = sentMoney;
    }

    public List<Operation> getReceivedMoney() {
        return receivedMoney;
    }

    public void setReceivedMoney(List<Operation> receivedMoney) {
        this.receivedMoney = receivedMoney;
    }

    public Status getStatus() {
        return Status;
    }

    public void setAccountStatus(Status status) {
        this.Status = status;
    }

    public LocalDate getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(LocalDate localdate) {
        this.monthlyMaintenanceFee = localdate;
    }

    public abstract void setStatus(enums.Status Status);

    public Object getAccountStatus() {
        return null;
    }
}
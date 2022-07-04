package model;

import enums.Role;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name ="id_account_holder")
public class AccountHolder extends User {

    private LocalDate dateOfBirth;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "address", column = @Column(name = "primary_address")),
    })
    private Address primaryAddress;

    @Column(name = "mailing_address")
    private String mailingAddress;

    public AccountHolder(String name, String username, String password, LocalDate dateOfBirth, classes.Address address) {
    }

    public AccountHolder(String name, String password, Set<Roles> roles, LocalDate dateOfBirth, Address primaryAddress,String mailingAddress) {
        super(name, password, roles);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    //SETTERS
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }
    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    //GETTERS
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public Address getPrimaryAddress() {
        return primaryAddress;
    }
    public String getMailingAddress() {
        return mailingAddress;
    }

}

package model;

import javax.persistence.Entity;
import java.util.Set;

@Entity
public class Admin extends User {

    public Admin(String name, String username, String password) {
    }

    public Admin(String name, String password, Set<Roles> roles) {
        super(name, password, roles);
    }

}

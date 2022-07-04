package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.Role;

import javax.persistence.*;

@Entity
@Table(name = "role_table")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore //NO TENGO MUY CLARO QUÉ HACE
    private User user;

    public Roles(Role admin, AccountHolder user) {}

    public Roles(Role role, User user) {
        setRoles(role);
        setUser(user);
    }

    //SETTERS
    public void setRole(Roles role) {
        this.role = role;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setRoles(Roles role) {
        this.role = role;
    }
    public void setUser(User user)
    {this.user = user;
    }

    //GETTERS
    public Roles getRole() {
            return role;
       }

    public Long getId() {
        return id;
    }
    public Roles getRoles() {
        return role;
    }
    public User getUser() {
        return user;
    }

}

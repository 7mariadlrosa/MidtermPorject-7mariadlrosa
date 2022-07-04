package model;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ThirdParty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String hashKey;

    public ThirdParty() {}

    public ThirdParty(@NotNull String name, @NotNull String hashKey) {
        setName(name);
        setHashKey(hashKey);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHashKey() {
        return hashKey;
    }
    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}

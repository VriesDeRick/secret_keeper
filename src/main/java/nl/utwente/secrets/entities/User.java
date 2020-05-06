package nl.utwente.secrets.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    private String name;

    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Secret> secrets;

    public User() {
        this(0L, null, null, null);
    }

    public User(Long id, String name, String email, List<Secret> secrets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.secrets = secrets;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public List<Secret> getSecrets() {
        return secrets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

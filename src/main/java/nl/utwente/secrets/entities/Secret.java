package nl.utwente.secrets.entities;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
public class Secret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private final User author;

    private final String hash;

    private final ZonedDateTime createdAt;

    public Secret() {
        this(0L, null, null, null);
    }

    public Secret(long id, User author, String hash, ZonedDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.hash = hash;
        this.createdAt = createdAt;
    }

    public String getHash() {
        return hash;
    }

    public User getAuthor() {
        return author;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }
}

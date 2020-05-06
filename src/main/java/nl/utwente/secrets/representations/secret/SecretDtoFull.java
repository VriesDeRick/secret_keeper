package nl.utwente.secrets.representations.secret;


import nl.utwente.secrets.entities.Secret;
import nl.utwente.secrets.entities.User;
import nl.utwente.secrets.representations.user.UserDto;

import java.time.ZonedDateTime;

public class SecretDtoFull {

    private final long id;
    private final UserDto author;
    private final ZonedDateTime createdAt;

    public SecretDtoFull(Secret secret) {
        this(secret.getId(), new UserDto(secret.getAuthor()), secret.getCreatedAt());
    }

    public SecretDtoFull(long id, UserDto author, ZonedDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public UserDto getAuthor() {
        return author;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}

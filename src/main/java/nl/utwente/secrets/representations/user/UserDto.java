package nl.utwente.secrets.representations.user;

import nl.utwente.secrets.entities.User;

public class UserDto {
    private final long id;
    private final String name;
    private final String email;

    public UserDto(User user) {
        this(user.getId(), user.getName(), user.getEmail());
    }

    public UserDto(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

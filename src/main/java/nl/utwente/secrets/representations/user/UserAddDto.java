package nl.utwente.secrets.representations.user;

public class UserAddDto {
    private final String name;
    private final String email;

    public UserAddDto() {
        this(null, null);
    }

    public UserAddDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

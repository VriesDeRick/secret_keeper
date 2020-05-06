package nl.utwente.secrets.representations.user;

import java.util.List;

public class UserUpdateDto extends UserAddDto {
    private final List<String> secrets;

    public UserUpdateDto(String name, String email, List<String> secrets) {
        super(name, email);
        this.secrets = secrets;
    }

    public List<String> getSecrets() {
        return secrets;
    }
}

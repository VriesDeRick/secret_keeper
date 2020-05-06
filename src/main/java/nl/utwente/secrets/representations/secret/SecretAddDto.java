package nl.utwente.secrets.representations.secret;

public class SecretAddDto {
    private long userId;
    private String secret;

    public SecretAddDto(long userId, String secret) {
        this.userId = userId;
        this.secret = secret;
    }

    public long getUserId() {
        return userId;
    }

    public String getSecret() {
        return secret;
    }
}

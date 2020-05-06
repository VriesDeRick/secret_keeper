package nl.utwente.secrets.representations.secret;

public class SecretGuessDto {
    private final String guess;

    public SecretGuessDto() {
        this(null);
    }

    public SecretGuessDto(String guess) {
        this.guess = guess;
    }

    public String getGuess() {
        return guess;
    }
}

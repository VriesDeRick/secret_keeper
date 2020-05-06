package nl.utwente.secrets.services;

import nl.utwente.secrets.entities.Secret;
import nl.utwente.secrets.entities.SecretRepository;
import nl.utwente.secrets.entities.User;
import nl.utwente.secrets.exceptions.SecretInputInvalidException;
import nl.utwente.secrets.exceptions.SecretNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class SecretService {

    private final SecretRepository secretRepository;

    public SecretService(@Autowired SecretRepository secretRepository) {
        this.secretRepository = secretRepository;
    }

    private boolean verifySecretHash(Secret secret, String attempt) {
        return getEncoder().matches(attempt, secret.getHash());
    }

    private String hashInput(String input) {
        return getEncoder().encode(input);
    }

    public Secret getSecretById(long id) {
        Optional<Secret> result = secretRepository.findById(id);
        if (!result.isPresent()) {
            throw new SecretNotFoundException();
        }
        return result.get();
    }

    public List<Secret> getSecretsByAuthor(User author) {
        return secretRepository.findAllByAuthor(author);
    }

    public List<Secret> getAllSecrets() {
        return secretRepository.findAll();
    }

    public Secret addSecret(User author, String input) {
        if (!isSecretInputValid(input)) {
            throw new SecretInputInvalidException();
        }
        Secret secret = new Secret(0, author, hashInput(input), ZonedDateTime.now());
        return secretRepository.save(secret);
    }


    public boolean checkSecretById(long id, String guess) {
        return checkSecret(getSecretById(id), guess);
    }

    public boolean checkSecret(Secret secret, String guess) {
        if (!isSecretInputValid(guess)) {
            throw new SecretInputInvalidException();
        }
        return verifySecretHash(secret, guess);
    }

    public boolean checkSecrets(List<String> guesses, List<Secret> secrets) {
        if (!guesses.isEmpty() && guesses.stream().anyMatch(s -> !isSecretInputValid(s))) {
            throw new SecretInputInvalidException();
        }
        if (guesses.size() != secrets.size()) {
            return false;
        }
        return guesses.stream().allMatch(guess -> secrets.stream().anyMatch(secret -> verifySecretHash(secret, guess)));
    }

    public static boolean isSecretInputValid(String input) {
        return input != null && !input.isEmpty();
    }

    private PasswordEncoder getEncoder() {
        return new Argon2PasswordEncoder();
    }

}

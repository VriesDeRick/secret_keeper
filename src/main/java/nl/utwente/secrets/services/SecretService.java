package nl.utwente.secrets.services;

import nl.utwente.secrets.Logger;
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

    private static final Logger logger = new Logger();
    private final SecretRepository secretRepository;

    public SecretService(@Autowired SecretRepository secretRepository) {
        this.secretRepository = secretRepository;
    }

    private boolean verifySecretHash(Secret secret, String attempt) {
        logger.srvLog("verifySecretHash");
        return getEncoder().matches(attempt, secret.getHash());
    }

    private String hashInput(String input) {
        logger.srvLog("hashInput");
        return getEncoder().encode(input);
    }

    public Secret getSecretById(long id) {
        logger.srvLog("getSecretById");
        Optional<Secret> result = secretRepository.findById(id);
        if (!result.isPresent()) {
            SecretNotFoundException e = new SecretNotFoundException();
            logger.errLog("getSecretById", e);
            throw e;
        }
        return result.get();
    }

    public List<Secret> getSecretsByAuthor(User author) {
        logger.srvLog("getSecretsByAuthor");
        return secretRepository.findAllByAuthor(author);
    }

    public List<Secret> getAllSecrets() {
        logger.srvLog("getAllSecrets");
        return secretRepository.findAll();
    }

    public Secret addSecret(User author, String input) {
        logger.srvLog("addSecret");
        if (!isSecretInputValid(input)) {
            SecretInputInvalidException e = new SecretInputInvalidException();
            logger.errLog("addSecret", e);
            throw e;
        }
        Secret secret = new Secret(0, author, hashInput(input), ZonedDateTime.now());
        return secretRepository.save(secret);
    }


    public boolean checkSecretById(long id, String guess) {
        logger.srvLog("checkSecretById");
        return checkSecret(getSecretById(id), guess);
    }

    public boolean checkSecret(Secret secret, String guess) {
        logger.srvLog("checkSecret");
        if (!isSecretInputValid(guess)) {
            SecretInputInvalidException e = new SecretInputInvalidException();
            logger.errLog("checkSecret", e);
            throw e;
        }
        return verifySecretHash(secret, guess);
    }

    public boolean checkSecrets(List<String> guesses, List<Secret> secrets) {
        logger.srvLog("checkSecrets");
        if (!guesses.isEmpty() && guesses.stream().anyMatch(s -> !isSecretInputValid(s))) {
            SecretInputInvalidException e = new SecretInputInvalidException();
            logger.errLog("checkSecrets", e);
            throw e;
        }
        if (guesses.size() != secrets.size()) {
            return false;
        }
        return guesses.stream().allMatch(guess -> secrets.stream().anyMatch(secret -> verifySecretHash(secret, guess)));
    }

    public static boolean isSecretInputValid(String input) {
        logger.srvLog("isSecretInputValid");
        return input != null && !input.isEmpty();
    }

    private PasswordEncoder getEncoder() {
        logger.srvLog("getEncoder");
        return new Argon2PasswordEncoder();
    }

}

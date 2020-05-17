package nl.utwente.secrets.services;

import nl.utwente.secrets.Logger;
import nl.utwente.secrets.entities.Secret;
import nl.utwente.secrets.entities.User;
import nl.utwente.secrets.entities.UserRepository;
import nl.utwente.secrets.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Component
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final SecretService secretService;
    private final Logger logger = new Logger();

    public UserService(@Autowired UserRepository userRepository, @Autowired SecretService secretService) {
        this.userRepository = userRepository;
        this.secretService = secretService;
    }

    public User getUserById(long id) {
        logger.srvLog("getUserById");
        Optional<User> result = userRepository.findById(id);
        if (!result.isPresent()) {
            UserNotFoundException e = new UserNotFoundException();
            logger.errLog("getUserById", e);
            throw e;
        }
        return result.get();

    }

    public List<User> getAllUsers() {
        logger.srvLog("getAllUsers");
        return userRepository.findAll();
    }

    public User addUser(String name, String email) {
        logger.srvLog("addUser");
        User user = new User(0L, name, email, new LinkedList<>());
        return userRepository.save(user);
    }

    /**
     * Updates details of the user.
     * Only allowed when all secrets by user are given as "authentication"
     */
    public User updateUser(long existingId, String name, String email, List<String> secrets) {
        logger.srvLog("updateUser");
        User existing = getUserById(existingId);

        // Check pre-condition: only allowed if secrets given are all secrets from this user
        if (!secretService.checkSecrets(secrets, secretService.getSecretsByAuthor(existing))) {
            UserNotFoundException e = new UserNotFoundException();
            logger.errLog("updateUser", e);
            throw e;
        }
        existing.setName(name);
        existing.setEmail(email);
        return existing;
    }

}

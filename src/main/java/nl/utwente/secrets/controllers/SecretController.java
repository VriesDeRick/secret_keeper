package nl.utwente.secrets.controllers;

import nl.utwente.secrets.Logger;
import nl.utwente.secrets.entities.Secret;
import nl.utwente.secrets.representations.secret.SecretAddDto;
import nl.utwente.secrets.representations.secret.SecretDtoFull;
import nl.utwente.secrets.representations.secret.SecretGuessDto;
import nl.utwente.secrets.services.SecretService;
import nl.utwente.secrets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.Control;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/secrets")
@Transactional
public class SecretController {

    private final Logger logger = new Logger();

    private final SecretService secretService;

    private final UserService userService;

    public SecretController(@Autowired SecretService secretService, UserService userService) {
        this.secretService = secretService;
        this.userService = userService;
    }


    @PostMapping(path = "/{id}")
    public boolean guessSecret(@PathVariable long id, @RequestBody SecretGuessDto dto) {
        long startTime = System.currentTimeMillis();
        try {
            return secretService.checkSecretById(id, dto.getGuess());
        } finally {
            logger.cntrlLog("guessSecret", System.currentTimeMillis() - startTime);
        }
    }

    @GetMapping(path = "/")
    public List<SecretDtoFull> getAllSecrets() {
        long startTime = System.currentTimeMillis();
        try {
            return secretService.getAllSecrets().stream().map(SecretDtoFull::new).collect(Collectors.toList());
        } finally {
            logger.cntrlLog("getAllSecrets", System.currentTimeMillis() - startTime);
        }
    }

    @PostMapping(path = "/")
    public SecretDtoFull addSecret(@RequestBody SecretAddDto dto) {
        long startTime = System.currentTimeMillis();
        try {
            Secret secret = secretService.addSecret(userService.getUserById(dto.getUserId()), dto.getSecret());
            return new SecretDtoFull(secret);
        } finally {
            logger.cntrlLog("addSecret", System.currentTimeMillis() - startTime);
        }
    }




}
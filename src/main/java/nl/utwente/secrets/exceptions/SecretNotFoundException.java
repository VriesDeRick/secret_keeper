package nl.utwente.secrets.exceptions;

import org.springframework.http.HttpStatus;

public class SecretNotFoundException extends RestException {

    public SecretNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Secret could not be found.");
    }
}

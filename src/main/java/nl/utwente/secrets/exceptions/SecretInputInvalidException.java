package nl.utwente.secrets.exceptions;

import org.springframework.http.HttpStatus;

public class SecretInputInvalidException extends RestException {
    public SecretInputInvalidException() {
        super(HttpStatus.BAD_REQUEST, "Given input cannot be used as a secret.");
    }
}

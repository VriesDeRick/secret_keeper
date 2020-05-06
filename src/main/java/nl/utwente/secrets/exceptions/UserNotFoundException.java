package nl.utwente.secrets.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RestException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User could not be found.");
    }
}

package nl.utwente.secrets.exceptions;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {
    private final HttpStatus code;

    public RestException(HttpStatus code, String message) {
        super(message);
        this.code = code;
    }

    public HttpStatus getCode() {
        return code;
    }
}

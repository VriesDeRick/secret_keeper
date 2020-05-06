package nl.utwente.secrets.controllers;

import nl.utwente.secrets.exceptions.RestException;
import nl.utwente.secrets.representations.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RestException.class)
    public ResponseEntity handleException(RestException exception, WebRequest request) {
        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorDto dto = new ErrorDto(uri, exception.getMessage());
        return handleExceptionInternal(exception, dto, new HttpHeaders(), exception.getCode(), request);
    }
}

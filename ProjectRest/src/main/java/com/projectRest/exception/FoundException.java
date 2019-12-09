package com.projectRest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FoundException extends RuntimeException {


    private static final long serialVersionUID = -5798132769496018860L;

    public FoundException(String entityName, Long id) {
        super(String.format("%s %d existe", entityName, id));
    }

    public FoundException(String entityName, String name) {
        super(String.format("%s %s existe", entityName, name));
    }

    public FoundException(String entityName, String message, String name) {
        super(String.format("%s %s %s", entityName, name, message));
    }

    public FoundException(String name) {
        super(name);
    }
}

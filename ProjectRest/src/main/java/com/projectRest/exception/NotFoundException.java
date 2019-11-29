package com.projectRest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {


    private static final long serialVersionUID = -5798132769496018860L;

    public NotFoundException(String entityName, Long id) {
        super(String.format("%s %d no existe", entityName, id));
    }

    public NotFoundException(String entityName, String name) {
        super(String.format("%s %s no existe", entityName, name));
    }

    public NotFoundException(String entityName, String message, String name) {
        super(String.format("%s %s %s", entityName, name, message));
    }

    public NotFoundException(String name) {
        super(name);
    }
}

package com.projectRest.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RolNotFoundException extends RuntimeException {


    private static final long serialVersionUID = -5798132769496018860L;

    public RolNotFoundException(Long id) {
        super(String.format("El rol  %d no existe", id));
    }

    public RolNotFoundException(String name) {
        super(String.format("El rol %s no existe", name));
    }

    public RolNotFoundException(String message, String name) {
        super(String.format("El rol %s %s",name, message));
    }
}

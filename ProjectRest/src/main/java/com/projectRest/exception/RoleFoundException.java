package com.projectRest.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RoleFoundException extends RuntimeException {


    private static final long serialVersionUID = -5798132769496018860L;

    public RoleFoundException(String name) {
        super(String.format("El rol %s ya existe", name));
    }

}

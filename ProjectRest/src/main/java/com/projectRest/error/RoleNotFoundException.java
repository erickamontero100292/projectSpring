package com.projectRest.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends RuntimeException {


    private static final long serialVersionUID = -5798132769496018860L;

    public RoleNotFoundException(Long id) {
        super(String.format("El rol  %d no existe", id));
    }

    public RoleNotFoundException(String name) {
        super(String.format("El rol %s no existe", name));
    }

    public RoleNotFoundException(String message, String name) {
        super(String.format("El rol %s %s",name, message));
    }
}

package com.projectRest.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Formato incorrecto")
public class BadRequestException extends RuntimeException {


    private static final long serialVersionUID = -5798132769496018860L;

    public BadRequestException(String name, Long id) {
        super(String.format("La %s con id %d no existe", name, id));
    }

    public BadRequestException(String entityName, String name) {
        super(String.format("La %s %s no existe", entityName, name));
    }

    public BadRequestException( String action,String entityName, String message) {
        super(String.format("El %s de %s %s", action, entityName, message));
    }
}

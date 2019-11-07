package com.projectRest.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WorkdayNotFoundException extends RuntimeException {


    private static final long serialVersionUID = -5798132769496018860L;

    public WorkdayNotFoundException(Long id) {
        super(String.format("La jornada %d no existe", id));
    }

    public WorkdayNotFoundException(String name) {
        super(String.format("La jornada %s no existe", name));
    }

    public WorkdayNotFoundException(String message,String name) {
        super(String.format("La jornada %s %s",name, message));
    }
}

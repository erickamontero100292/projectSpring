package com.projectRest.controller;


import com.projectRest.error.ErrorRest;
import com.projectRest.response.ResponseRest;
import com.projectRest.model.Workday;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
@RequestMapping("/api")
public class WorkDayController {
    List<Workday> entityWorkdays;


    @GetMapping("/workdays")
    public List<Workday> getWorkday() {

        return entityWorkdays;

    }

    @GetMapping("/workday/{id}")
    public Workday getWorkdayById(@PathVariable Long id) {
        if (id > 0 && id <= entityWorkdays.size()) {
            return entityWorkdays.get((int) (id - 1));
        } else {
            throw new WorkdayNotFoundException(id);
        }


    }

    @GetMapping("/workday/findname/{name}")
    public Workday getWorkdayByName(@PathVariable String name) {
        Workday workday = null;
        for (Workday model : entityWorkdays) {
            if (model.getName().equalsIgnoreCase(name)) {
                workday = model;
            }
        }
        if (workday != null) {
            return workday;
        } else {
            throw new WorkdayNotFoundException(name);
        }
    }

    @PostConstruct
    private void init() {
        Workday workday8H = new Workday(1L, "Jornada laboral 8H", 8L, 40L);
        Workday workday12H = new Workday(2L, "Jornada laboral 12H", 12L, 60L);
        entityWorkdays = new ArrayList<>();
        entityWorkdays.add(workday8H);
        entityWorkdays.add(workday12H);

    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class WorkdayNotFoundException extends RuntimeException {

        private static final long serialVersionUID = -5798132769496018860L;

        public WorkdayNotFoundException(Long id) {
            super(String.format("La jornada %d no existe", id));
        }

        public WorkdayNotFoundException(String name) {
            super(String.format("La jornada %s no existe", name));
        }

    }

    @PostMapping("/addworkday")
    public ResponseEntity<?> addworkday(RequestEntity<Workday> requestEntity) {

        if (requestEntity.getBody() == null) {
            return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos de la jornada"),
                    HttpStatus.BAD_REQUEST);
        }

        Workday workday = requestEntity.getBody();

        if (workday.getId() == null) {
            return new ResponseEntity<ErrorRest>(new ErrorRest("La jornada con ID " + workday.getId() + " ya existe"),
                    HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<Workday>(addListWorkday(workday), HttpStatus.CREATED);
        }
    }

    @PostMapping("/addListworkday")
    public ResponseEntity<?> addworkdays(RequestEntity<List<Workday>> requestEntity) {

        if (requestEntity.getBody() == null) {
            return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos de la jornada"),
                    HttpStatus.BAD_REQUEST);
        }

        List<Workday> workday = requestEntity.getBody();

        if (workday == null) {
            return new ResponseEntity<ErrorRest>(new ErrorRest("La jornada con ID " + " ya existe"),
                    HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<ResponseRest>(addListWorkday(workday), HttpStatus.CREATED);
        }
    }

    public Workday addListWorkday(Workday workday) {
        entityWorkdays.add(workday);
        return workday;
    }

    public ResponseRest addListWorkday(List<Workday> workdays) {
        ResponseRest responseRest = new ResponseRest("Jornadas agregadas", HttpStatus.CREATED);
        for(Iterator<Workday> itr = workdays.iterator(); itr.hasNext();){
            Workday workday = itr.next();
            entityWorkdays.add(workday);
        }

        return responseRest;
    }
}



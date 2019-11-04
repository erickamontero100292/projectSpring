package com.projectRest.controller;


import com.projectRest.entity.EntityWorkday;
import com.projectRest.error.ErrorRest;
import com.projectRest.helper.WorkdayHelper;
import com.projectRest.model.Workday;
import com.projectRest.repository.WorkDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class WorkDayController {

    List<Workday> entityWorkdays;

  @Autowired
    WorkdayHelper workdayHelper;
    @Autowired
    WorkDayRepository workDayRepository;

    @GetMapping("/workdays")
    public List<Workday> getWorkday() {

        return entityWorkdays;

    }

    @GetMapping("/workday/{id}")
    public Workday getWorkdayById(@PathVariable Long id) {

        if (id > 0) {
            EntityWorkday entityWorkday = workDayRepository.findById(id).get();
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
        EntityWorkday workday8EntityWorkdayH = new EntityWorkday(1L, "Jornada laboral 8H", 8L, 40L);
        Workday workday12H = new Workday(2L, "Jornada laboral 12H", 12L, 60L);
        EntityWorkday workday12EntityWorkdayH = new EntityWorkday(2L, "Jornada laboral 12H", 12L, 60L);
        entityWorkdays = new ArrayList<>();
        entityWorkdays.add(workday8H);
        entityWorkdays.add(workday12H);

        EntityWorkday entityWorkday;
        entityWorkday =workdayHelper.save(workday8EntityWorkdayH);
        EntityWorkday save = workdayHelper.save(workday12EntityWorkdayH);


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
            return new ResponseEntity<>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos de la jornada"),
                    HttpStatus.BAD_REQUEST);
        }

        Workday workday = requestEntity.getBody();
        EntityWorkday entityWorkday = null;
        try {
            entityWorkday = workDayRepository.findByName_IgnoreCase(workday.getName());

            if (entityWorkday == null) {
                return new ResponseEntity<Workday>(workdayHelper.addWorkday(workday,entityWorkdays), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(new ErrorRest("La jornada con: " + workday.getName() + " ya existe"),
                        HttpStatus.CONFLICT);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(new ErrorRest("Error creando la jornada"),
                    HttpStatus.CONFLICT);
        }


    }

    @PostMapping("/addListworkday")
    public ResponseEntity<?> addworkdays(RequestEntity<List<Workday>> requestEntity) {

        if (requestEntity.getBody() == null) {
            return new ResponseEntity<>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos de la jornada"),
                    HttpStatus.BAD_REQUEST);
        }

        List<Workday> workday = requestEntity.getBody();

        if (workday == null) {
            return new ResponseEntity<>(new ErrorRest("La jornada con ID " + " ya existe"),
                    HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(workdayHelper.addListWorkday(workday,entityWorkdays), HttpStatus.CREATED);
        }
    }




}



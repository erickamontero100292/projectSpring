package com.projectRest.controller;


import com.projectRest.entity.EntityWorkday;
import com.projectRest.error.ErrorRest;
import com.projectRest.error.WorkdayNotFoundException;
import com.projectRest.helper.WorkdayHelper;
import com.projectRest.model.Workday;
import com.projectRest.repository.WorkDayRepository;
import com.projectRest.service.WorkDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class WorkDayController {

    @Autowired
    WorkDayRepository workDayRepository;
    @Autowired
    WorkDayService workDayService;

    @Autowired
    WorkdayHelper workdayHelper;


    @GetMapping("/workday/{id}")
    public Workday getWorkdayById(@PathVariable Long id) {
        Workday entityWorkday = null;
        try {
            if (id > 0) {
                entityWorkday = workDayService.findById(id);
                return entityWorkday;
            }
        } catch (WorkdayNotFoundException e) {
            throw e;
        }
        return entityWorkday;
    }

    @GetMapping("/workday/findname/{name}")
    public Workday getWorkdayByName(@PathVariable String name) {
        Workday entityWorkday = null;
        try {
            if (name != null) {
                entityWorkday = workDayService.findByName(name);
                return entityWorkday;
            }
        } catch (WorkdayNotFoundException e) {
            throw e;
        }
        return entityWorkday;
    }


    @PostMapping("/addworkday")
    public ResponseEntity<?> addworkday(RequestEntity<Workday> requestEntity) {
        if (workdayHelper.validateBodyRequest(requestEntity)) {
            return new ResponseEntity<>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos de la jornada"),
                    HttpStatus.BAD_REQUEST);

        } else {
            Workday requestEntityBody = requestEntity.getBody();
            try {
                Workday workDayServiceByName = workDayService.findByName(requestEntityBody.getName());

                if (!workdayHelper.isWorkdayNotNull(workDayServiceByName)) {
                    return new ResponseEntity<>(workDayService.save(requestEntityBody), HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(new ErrorRest("La jornada con: " + workDayServiceByName.getName() + " no existe"),
                            HttpStatus.CONFLICT);
                }
            } catch (WorkdayNotFoundException e) {
                return new ResponseEntity<>(new ErrorRest("La jornada con: " + requestEntityBody.getName() + " ya existe"),
                        HttpStatus.CONFLICT);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>(new ErrorRest("Error creando la jornada"),
                        HttpStatus.CONFLICT);
            }
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
            return new ResponseEntity<>(workDayService.saveList(workday), HttpStatus.CREATED);
        }
    }


    @GetMapping("/listworkday")
    public ResponseEntity<?> listWorkdays() {
        List<Workday> workdayList = workDayService.findAll();

        if (workdayList == null) {
            return new ResponseEntity<>(new ErrorRest("No se pudo obtener la informacion de la lista"),
                    HttpStatus.CONFLICT);
        }
        if (workdayList.isEmpty()) {
            return new ResponseEntity<>(new ErrorRest("No existen jornadas"),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(workdayList, HttpStatus.OK);
        }
    }


    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateWorkday(RequestEntity<Workday> requestEntity) {

        if (workdayHelper.validateBodyRequest(requestEntity)) {
            return new ResponseEntity<>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos de la jornada"),
                    HttpStatus.BAD_REQUEST);

        } else {
            Workday requestEntityBody = requestEntity.getBody();
            if (!workdayHelper.isWorkdayCorrect(requestEntityBody)) {
                return new ResponseEntity<>(new ErrorRest("Formato de petición incorrecto. Debe enviar los datos de la jornada"),
                        HttpStatus.BAD_REQUEST);
            } else {
                try {
                    Workday workDayServiceByName = workDayService.findByName(requestEntityBody.getName());
                    if (!workdayHelper.isWorkdayNotNull(workDayServiceByName)) {
                        requestEntityBody = new Workday(workDayServiceByName);
                        return new ResponseEntity<>(workDayService.update(requestEntityBody), HttpStatus.CREATED);
                    } else {
                        return new ResponseEntity<>(new ErrorRest("La jornada con nombre " + requestEntityBody.getName() + " no se pudo actualizar"),
                                HttpStatus.CONFLICT);
                    }
                } catch (WorkdayNotFoundException e) {
                    return new ResponseEntity<>(new ErrorRest("La jornada con: " + requestEntityBody.getName() + " ya existe"),
                            HttpStatus.CONFLICT);
                } catch (NoSuchElementException e) {
                    return new ResponseEntity<>(new ErrorRest("Error creando la jornada"),
                            HttpStatus.CONFLICT);
                }
            }
        }
    }

    @PostConstruct
    private void init() {
        EntityWorkday workday8EntityWorkdayH = new EntityWorkday(1L, "Jornada laboral 8H", 8L, 40L);
        EntityWorkday workday12EntityWorkdayH = new EntityWorkday(2L, "Jornada laboral 12H", 12L, 60L);
        workDayService.save(workday8EntityWorkdayH);
        workDayService.save(workday12EntityWorkdayH);
    }

}



package com.projectRest.controller;


import com.projectRest.model.Workday;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkDayController {
    List<Workday> entityWorkdays;


    @GetMapping("/workday")
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
        entityWorkdays = Arrays.asList(workday8H, workday12H);

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

}



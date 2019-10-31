package com.projectRest.controller;


import com.projectRest.model.Workday;
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
        }

        return null;
    }

    @GetMapping("/workday/findname/{name}")
    public Workday getWorkdayByName(@PathVariable  String name) {

        for (Workday workday : entityWorkdays) {
            if (workday.getName().equalsIgnoreCase(name)) {
                return workday;
            }
        }


        return null;
    }

    @PostConstruct
    private void init() {
        Workday workday8H = new Workday(1L, "Jornada laboral 8H", 8L, 40L);
        Workday workday12H = new Workday(2L, "Jornada laboral 12H", 12L, 60L);
        entityWorkdays = Arrays.asList(workday8H, workday12H);

    }
}



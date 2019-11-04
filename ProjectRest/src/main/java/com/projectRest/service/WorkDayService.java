package com.projectRest.service;

import com.projectRest.controller.WorkDayController;
import com.projectRest.entity.EntityWorkday;
import com.projectRest.error.WorkdayNotFoundException;
import com.projectRest.model.Workday;
import com.projectRest.repository.WorkDayRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkDayService {

    private WorkDayRepository repository;

    public WorkDayService(WorkDayRepository repository) {

        this.repository = repository;
    }

    public Workday save(EntityWorkday workday) {
        EntityWorkday entityWorkday = repository.save(workday);
        Workday newWorkday = new Workday(entityWorkday);
        return newWorkday;
    }

    public List<Workday> findAll() {
        List<EntityWorkday> entityWorkdays = repository.findAll();
        List<Workday> workdays = new ArrayList<>();
        for (EntityWorkday entityWorkday : entityWorkdays) {
            workdays.add(new Workday(entityWorkday));
        }

        return workdays;
    }

    public Workday findById(Long id) {
        Workday workday = null;
        try {
            EntityWorkday entityWorkday = repository.findById(id).orElse(null);
            workday = new Workday(entityWorkday);
        } catch (WorkdayNotFoundException  e) {
			throw new WorkdayNotFoundException(id);
        } catch (Exception e) {
			throw new WorkdayNotFoundException(id);
        }

        return workday;

    }

    public Workday delete(Long id) {
        EntityWorkday workday = repository.findById(id).orElse(null);
        repository.delete(workday);
        Workday deleteWorkday = new Workday(workday);
        return deleteWorkday;
    }

}

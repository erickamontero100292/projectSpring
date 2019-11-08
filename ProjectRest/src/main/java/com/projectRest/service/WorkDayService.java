package com.projectRest.service;

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
        return new Workday(entityWorkday);
    }

    public Workday save(Workday workday) {
        EntityWorkday entityWorkday = null;
        try {
            entityWorkday = new EntityWorkday(workday);
            entityWorkday = repository.save(entityWorkday);
        } catch (Exception e) {
            throw new WorkdayNotFoundException(workday.getName());
        }
        return new Workday(entityWorkday);
    }

    public Workday update(Workday workday) {
        EntityWorkday entityWorkday = null;
        try {
            entityWorkday = repository.findByName_IgnoreCase(workday.getName());
            entityWorkday.updateWorkday(workday);
            entityWorkday = repository.save(entityWorkday);
        } catch (Exception e) {
            throw new WorkdayNotFoundException(workday.getName()," no se logro actualizar");
        }
        return new Workday(entityWorkday);
    }

    public boolean delete(Workday workday) {
        EntityWorkday entityWorkday = null;
        boolean isDelete;
        try {
            entityWorkday = repository.findByName_IgnoreCase(workday.getName());
            repository.delete(entityWorkday);
            isDelete=true;
        } catch (Exception e) {
            throw new WorkdayNotFoundException(workday.getName()," no se logro actualizar");
        }
        return isDelete;
    }

    public List<EntityWorkday> saveList(List<Workday> workdayList) {
        List<EntityWorkday> entityWorkdays = new ArrayList<>();
        try {
            for (Workday workday : workdayList) {
                entityWorkdays.add(new EntityWorkday(workday));
            }
            repository.saveAll(entityWorkdays);
        } catch (Exception e) {
            throw new WorkdayNotFoundException("workday");
        }
        return entityWorkdays;
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
        } catch (Exception e) {
            throw new WorkdayNotFoundException(id);
        }

        return workday;

    }

    public Workday findByName(String name) {
        Workday workday = null;
        try {
            EntityWorkday entityWorkday = repository.findByName_IgnoreCase(name);
            workday = new Workday(entityWorkday);
        } catch (NullPointerException e) {
            workday = new Workday();
        } catch (Exception e) {
            throw new WorkdayNotFoundException(name);
        }

        return workday;

    }

    public Workday delete(Long id) {
        EntityWorkday workday = repository.findById(id).orElse(null);
        repository.delete(workday);
        return new Workday(workday);
    }

}

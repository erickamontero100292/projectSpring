package com.projectRest.helper;

import com.projectRest.entity.EntityWorkday;
import com.projectRest.model.Workday;
import com.projectRest.repository.WorkDayRepository;
import com.projectRest.response.ResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class WorkdayHelper {
    @Autowired
    WorkDayRepository workDayRepository;

    public Workday addWorkday(Workday workday, List<Workday> entityWorkdays) {
        EntityWorkday entityWorkday = new EntityWorkday(workday.getName(), workday.getNumberDailyHour(), workday.getNumberWeekHour());
        entityWorkday = workDayRepository.save(entityWorkday);
        Workday newWorkday = new Workday(entityWorkday.getId(), entityWorkday.getName(), entityWorkday.getNumberDailyHour(), entityWorkday.getNumberWeekHour());
        entityWorkdays.add(workday);
        return newWorkday;
    }

    public ResponseRest addListWorkday(List<Workday> workdays, List<Workday> entityWorkdays) {
        ResponseRest responseRest = new ResponseRest("Jornadas agregadas", HttpStatus.CREATED);
        for (Iterator<Workday> itr = workdays.iterator(); itr.hasNext(); ) {
            Workday workday = itr.next();
            entityWorkdays.add(workday);
        }

        return responseRest;
    }

    public Workday addListWorkday(Workday workday, List<Workday> entityWorkdays) {
        entityWorkdays.add(workday);
        return workday;
    }

    public EntityWorkday save(EntityWorkday entityWorkday) {
       return workDayRepository.save(entityWorkday);
    }
}

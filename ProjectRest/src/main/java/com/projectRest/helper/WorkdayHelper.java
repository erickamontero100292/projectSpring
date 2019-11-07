package com.projectRest.helper;

import com.projectRest.model.Workday;
import com.projectRest.repository.WorkDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

@Component
public class WorkdayHelper {

    public boolean isWorkdayNotNull(Workday workDayServiceByName) {
        return workDayServiceByName == null || workDayServiceByName.getName() == null;
    }

    public boolean validateBodyRequest(RequestEntity<Workday> requestEntity) {
        if (requestEntity.getBody() == null) {
            return true;
        }
        return false;
    }
    public boolean isWorkdayCorrect(Workday workday) {
        if (workday.getName() == null || workday.getName().isEmpty()) {
            return false;
        }
        return true;
    }


}

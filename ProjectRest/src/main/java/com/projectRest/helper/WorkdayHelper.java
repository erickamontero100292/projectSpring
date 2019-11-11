package com.projectRest.helper;

import com.projectRest.model.Workday;
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



}

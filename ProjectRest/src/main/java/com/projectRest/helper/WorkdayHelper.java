package com.projectRest.helper;

import com.projectRest.error.ErrorResponse;
import com.projectRest.error.WorkdayNotFoundException;
import com.projectRest.model.Workday;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public List<ErrorResponse> validateWorkdayDuplicate(List<Workday> workdayList) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        Iterator iterator = workdayList.iterator();
        List<Workday> arrayList = new ArrayList<>(workdayList);
        try {

            while (iterator.hasNext()) {
                Workday workday = (Workday) iterator.next();
                arrayList.remove(workday);
                for (Workday workday1 : arrayList) {
                    if (workday.equals(workday1)) {
                        ErrorResponse errorResponse = ErrorResponse.generateError(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), "Existen elementos duplicados en la lista");
                        errorResponses.add(errorResponse);

                    }
                }
            }

        } catch (WorkdayNotFoundException e) {
            //TODO  PUT LOG
        } catch (Exception e) {
            throw new WorkdayNotFoundException("workday");
        }
        return errorResponses;
    }

}

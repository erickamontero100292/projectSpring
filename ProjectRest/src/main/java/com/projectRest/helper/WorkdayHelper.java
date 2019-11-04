package com.projectRest.helper;

import com.projectRest.repository.WorkDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkdayHelper {
    @Autowired
    WorkDayRepository workDayRepository;


}

package com.projectRest.helper;

import com.projectRest.model.Workday;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

@Component
public class Validations<T> {

    public boolean validateBodyRequest(RequestEntity<Object> requestEntity) {
        if (requestEntity.getBody() == null) {
            return true;
        }
        return false;
    }
}

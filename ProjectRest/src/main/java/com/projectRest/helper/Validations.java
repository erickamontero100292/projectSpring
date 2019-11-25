package com.projectRest.helper;

import com.projectRest.model.Workday;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Validations<T> {

    public boolean isNullBody(RequestEntity<Object> requestEntity) {
        if (requestEntity.getBody() == null) {
            return true;
        }
        return false;
    }

    public boolean isNullOrEmpty(List<Object> list){
        return list == null || list.isEmpty();
    }

}

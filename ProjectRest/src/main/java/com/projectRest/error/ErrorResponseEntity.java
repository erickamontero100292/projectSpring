package com.projectRest.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponseEntity {


    public static ResponseEntity  getErrorResponseEntity(HttpStatus httpStatus, int code, String message) {
        ResponseEntity responseEntity;
        ErrorResponse errorResponse = ErrorResponse.generateError(httpStatus, code, message);
        responseEntity = new ResponseEntity<>(errorResponse, httpStatus);
        return responseEntity;
    }
}

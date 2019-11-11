package com.projectRest.error;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private String message;
    private HttpStatus status;
    private String code;
    private String detail;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public static ErrorResponse generateError(RuntimeException ex, HttpStatus status, int codeStatus, String message) {

        ErrorRestBuilder restBuilder = new ErrorRestBuilder();

        return restBuilder.withStatus(status)
                .withCode(String.valueOf(codeStatus))
                .withDetail(ex.getLocalizedMessage())
                .withMessage(message).build();
    }

    public static ErrorResponse generateError(HttpStatus status, int codeStatus, String message) {

        ErrorRestBuilder restBuilder = new ErrorRestBuilder();
        return restBuilder.withStatus(status)
                .withCode(String.valueOf(codeStatus)).withDetail("")
                .withMessage(message).build();
    }
}



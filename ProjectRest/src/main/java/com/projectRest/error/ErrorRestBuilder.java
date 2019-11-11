package com.projectRest.error;

import org.springframework.http.HttpStatus;

public class ErrorRestBuilder {

    private String message;
    private HttpStatus status;
    private String code;
    private String detail;


    public ErrorRestBuilder() {
    }

    public ErrorRestBuilder(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorRestBuilder withStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ErrorRestBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public ErrorRestBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorRestBuilder withDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public  ErrorResponse build() {
        ErrorResponse apiErrorResponse = new ErrorResponse();
        apiErrorResponse.setStatus(this.status);
        apiErrorResponse.setCode(this.code);
        apiErrorResponse.setDetail(this.detail);
        apiErrorResponse.setMessage(this.message);
        return apiErrorResponse;
    }
}

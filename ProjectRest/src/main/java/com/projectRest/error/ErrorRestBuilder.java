package com.projectRest.error;

import org.springframework.http.HttpStatus;

public class ErrorRestBuilder {

    private String message;
    private HttpStatus status;
    private String errorCode;
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

    public static ErrorRestBuilder anApiErrorResponse() {
        return new ErrorRestBuilder();
    }

    public ErrorRestBuilder withStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ErrorRestBuilder withError_code(String error_code) {
        this.errorCode = error_code;
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

    public ErrorResponse build() {
        ErrorResponse apiErrorResponse = new ErrorResponse();
        apiErrorResponse.setStatus(this.status);
        apiErrorResponse.setErrorCode(this.errorCode);
        apiErrorResponse.setDetail(this.detail);
        apiErrorResponse.setMessage(this.message);
        return apiErrorResponse;
    }
}

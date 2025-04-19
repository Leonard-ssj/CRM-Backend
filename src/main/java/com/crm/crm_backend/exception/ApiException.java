package com.crm.crm_backend.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}

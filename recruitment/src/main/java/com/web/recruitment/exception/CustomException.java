package com.web.recruitment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
@Getter
public class CustomException extends RuntimeException{
    private final Map<String, String> errors;
    private final String message;

    private final HttpStatus statusCode;
    public CustomException(String message, Map<String, Object> errors, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.errors = new HashMap<>();
        for (Map.Entry<String, Object> entry : errors.entrySet()) {
            this.errors.put(entry.getKey(), entry.getValue().toString());
        }
    }

    public CustomException(String message, String errorField, String errorMessage, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        errors = new HashMap<>();
        errors.put(errorField, errorMessage);
    }
}

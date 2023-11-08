package com.web.recruitment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class ValidationException extends CustomException{
    public ValidationException(Map<String, Object> errors) {
        super("InvalidInput", errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    public ValidationException(String errorField, String errorMessage) {
        super("InvalidInput", errorField, errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ValidationException(String message, String errorField, String errorMessage) {
        super(message, errorField, errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public ValidationException(String message, Map<String, Object> errors) {
        super(message, errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

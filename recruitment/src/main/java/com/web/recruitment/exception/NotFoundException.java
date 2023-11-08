package com.web.recruitment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class NotFoundException extends CustomException{
    public NotFoundException(Map<String, Object> errors) {
        super("NotFound", errors, HttpStatus.NOT_FOUND);
    }
    public NotFoundException(String errorField, String errorMessage) {
        super("NotFound", errorField, errorMessage, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message, Map<String, Object> errors) {
        super(message, errors, HttpStatus.NOT_FOUND);
    }
    public NotFoundException(String message, String errorField, String errorMessage) {
        super(message, errorField, errorMessage, HttpStatus.NOT_FOUND);
    }
}

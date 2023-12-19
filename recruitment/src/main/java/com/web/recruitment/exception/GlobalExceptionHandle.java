/*
package com.web.recruitment.exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception ex) {
        log.info("Handle Exception");
        return this.toResponseEntity("SomethingWentWrong", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Object> handleInternalServerErrorException(HttpServletRequest request, InternalServerErrorException ex) {
        log.info("Internal Server Error Exception");

        return this.toResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(HttpServletRequest request, BadRequestException ex) {
        log.info("Bad request");
        return this.toResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(HttpServletRequest request, NotFoundException ex) {
        log.info("Not Found");
        return this.toResponseEntity("NotFound", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(HttpServletRequest request, ValidationException ex) {
        log.info("Validation");
        return this.toResponseEntity(ex.getMessage(), ex.getErrors(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(HttpServletRequest request, CustomException ex) {
        log.info(ex.getMessage());
        return this.toResponseEntity(ex.getMessage(), ex.getErrors(), ex.getStatusCode());
    }

    private ResponseEntity<Object> toResponseEntity(String message, HttpStatus httpStatus) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return new ResponseEntity<>(response, httpStatus);
    }

    private ResponseEntity<Object> toResponseEntity(String message, Map<String, String> errors, HttpStatus httpStatus) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        if (!errors.isEmpty()) {
            response.put("errors", errors);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> errors = new HashMap<>();
        res.put("message", "InvalidInput");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        res.put("errors", errors);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}
*/

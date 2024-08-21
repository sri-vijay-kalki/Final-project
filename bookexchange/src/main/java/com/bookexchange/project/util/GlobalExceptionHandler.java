package com.bookexchange.project.util;

import com.bookexchange.project.exception.ConflictException;
import com.bookexchange.project.exception.UnAuthorizedException;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.hibernate.TransactionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.ServiceUnavailableException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<Void> handleUnAuthorizedException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> handleBadRequestException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Void> handleMediaTypeNotSupportedException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Void> handleRequestParamException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ServiceUnavailableException.class, ConnectException.class, TransactionException.class})
    public ResponseEntity<Void> handleServiceUnAvailableException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Void> handleConflictException(Exception ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }
}

package com.pdev.rempms.locationservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger logg = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {UnsupportedOperationException.class})
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<Object> unsupportedOperationException(UnsupportedOperationException ex) {
        logg.error("UnsupportedOperationException: {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Unsupported operation");
        details.add(ex.getMessage());
        ApiErrors errors = new ApiErrors(ex.getMessage(), details, HttpStatus.NOT_IMPLEMENTED, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(errors);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        logg.error("UnauthorizedException: {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Unauthorized Exception");
        details.add(ex.getMessage());
        ApiErrors errors = new ApiErrors(ex.getMessage(), details, HttpStatus.UNAUTHORIZED, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(value = {RecordFoundException.class})
    public ResponseEntity<Object> handleRecordFoundException(RecordFoundException ex) {
        logg.error("RecordFoundException: {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Record already exists");
        details.add(ex.getMessage());
        ApiErrors errors = new ApiErrors(ex.getMessage(), details, HttpStatus.FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FOUND).body(errors);
    }

    @ExceptionHandler(value = {RecordNotFoundException.class})
    public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex) {
        logg.error("RecordNotFoundException: {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Record not found");
        details.add(ex.getMessage());
        ApiErrors errors = new ApiErrors(ex.getMessage(), details, HttpStatus.NOT_FOUND, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(value = {ForbiddenAccessException.class})
    public ResponseEntity<Object> handleForbiddenAccessException(ForbiddenAccessException ex) {
        logg.error("ForbiddenAccessException: {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Access denied");
        details.add(ex.getMessage());
        ApiErrors errors = new ApiErrors(ex.getMessage(), details, HttpStatus.FORBIDDEN, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
    }

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<Object> handleBaseException(BaseException ex) {
        logg.error("BaseException: {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Base exception");
        details.add(ex.getMessage());
        ApiErrors errors = new ApiErrors(ex.getMessage(), details, HttpStatus.resolve(ex.getErrorCode()), LocalDateTime.now());
        return ResponseEntity.status(ex.getErrorCode()).body(errors);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception ex) {
        logg.error("Exception: {}", ex.getMessage());
        List<String> details = new ArrayList<>();
        details.add("Other exception");
        details.add(ex.getMessage());
        ApiErrors errors = new ApiErrors(ex.getMessage(), details, HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }

}

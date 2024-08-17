package com.pdev.rempms.documentservice.exception;

import com.pdev.rempms.documentservice.util.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<CommonResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        CommonResponse commonResponse = new CommonResponse();
        List<String> errorList = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> errorList.add(fieldError.getDefaultMessage())
        );
        log.error("MethodArgumentNotValidException : {}", ex.getMessage());
        commonResponse.setStatus(HttpStatus.BAD_REQUEST);
        commonResponse.setMessage(errorList.get(0));
        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponse);
    }

   /* @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<CommonResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        CommonResponse commonResponse = new CommonResponse();
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        log.error("MethodArgumentNotValidException : {}", ex.getMessage());
        commonResponse.setData(errorMap);
        commonResponse.setStatus(HttpStatus.BAD_REQUEST);
        commonResponse.setMessage("Unsuccessful.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(commonResponse);
    }*/
}

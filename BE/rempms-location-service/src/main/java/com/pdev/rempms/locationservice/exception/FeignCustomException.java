package com.pdev.rempms.locationservice.exception;

import lombok.Data;

import java.util.Map;

@Data
public class FeignCustomException extends RuntimeException {

    private static final long serialVersionUID = -127350004589811526L;

    private int status;
    private Map<String, Object> body;
    private String message;

    public FeignCustomException(int status, Map<String, Object> map) {
        this.status = status;
        this.body = map;
    }

    public FeignCustomException(int status, Map<String, Object> map, String message) {
        this.status = status;
        this.body = map;
        this.message = message;
    }

    public FeignCustomException(String message) {
        super(message);
    }
}

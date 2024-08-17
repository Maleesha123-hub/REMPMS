package com.pdev.rempms.locationservice.exception;

public class ForbiddenAccessException extends RuntimeException {

    private final long serialVersionUID = -127350004589811526L;

    public ForbiddenAccessException() {
    }

    public ForbiddenAccessException(String message) {
        super(message);
    }
}

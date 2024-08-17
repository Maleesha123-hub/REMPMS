package com.pdev.rempms.locationservice.exception;

public class RecordFoundException extends RuntimeException {

    private static final long serialVersionUID = 7426683700363353926L;

    public RecordFoundException(String message) {
        super(message);
    }
}

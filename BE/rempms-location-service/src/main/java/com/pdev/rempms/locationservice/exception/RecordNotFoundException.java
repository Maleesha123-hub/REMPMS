package com.pdev.rempms.locationservice.exception;

public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6638849627494498004L;

    public RecordNotFoundException(String message) {
        super(message);
    }
}


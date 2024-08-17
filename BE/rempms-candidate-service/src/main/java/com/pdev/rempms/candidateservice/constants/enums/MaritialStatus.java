package com.pdev.rempms.candidateservice.constants.enums;

public enum MaritialStatus {
    SINGLE("Single"),
    MARRIED("Married");

    private final String message;

    MaritialStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

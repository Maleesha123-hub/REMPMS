package com.pdev.rempms.recruitmentservice.constants.enums;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String message;

    Gender(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

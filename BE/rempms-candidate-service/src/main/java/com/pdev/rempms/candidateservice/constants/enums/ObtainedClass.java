package com.pdev.rempms.candidateservice.constants.enums;

public enum ObtainedClass {

    FIRST_CLASS("First class"),

    SECOND_CLASS_UPPER("Second class upper"),

    SECOND_CLASS_LOWER("Second class lower"),

    PASS("Pass"),

    PENDING_RESULTS("Pending results"),

    CURRENTLY_FOLLOWING("Currently following");

    private final String message;

    ObtainedClass(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

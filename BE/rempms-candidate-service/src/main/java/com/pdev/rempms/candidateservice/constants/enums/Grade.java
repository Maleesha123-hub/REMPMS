package com.pdev.rempms.candidateservice.constants.enums;

public enum Grade {

    HIGHER_DISTINCTION("Higher Distinction"),

    DISTINCTION("Distinction"),

    CREDIT_PASS("Credit Pass"),

    SIMPLE_PASS("Simple Pass"),

    FAIL("Fail");

    private final String message;

    Grade(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

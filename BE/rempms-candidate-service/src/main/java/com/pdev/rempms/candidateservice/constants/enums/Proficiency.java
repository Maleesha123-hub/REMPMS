package com.pdev.rempms.candidateservice.constants.enums;

public enum Proficiency {

    EXCELLENT("Excellent"),

    GOOD("Good"),

    AVERAGE("Average"),

    POOR("Poor");

    private final String message;

    Proficiency(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

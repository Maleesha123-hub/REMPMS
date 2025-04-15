package com.pdev.rempms.candidateservice.enums;

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

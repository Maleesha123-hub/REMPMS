package com.pdev.rempms.candidateservice.constants.enums;

public enum NoticePeriod {

    WEEKS2("2_weeks"),

    WEEKS3("3_weeks"),

    WEEKS4("4_weeks"),

    WEEKS5("5_weeks");

    private final String message;

    NoticePeriod(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

package com.pdev.rempms.recruitmentservice.constants.enums;

public enum Salutation {
    MR("Mr"),
    MRS("Mrs");

    private final String message;

    Salutation(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

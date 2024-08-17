package com.pdev.rempms.candidateservice.constants.enums;

public enum SchoolQualification {
    G_C_E_A_L("G.C.E A/L"),
    G_C_E_O_L("G.C.E O/L");

    private final String message;

    SchoolQualification(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

package com.pdev.rempms.candidateservice.constants.enums;

public enum AwardType {
    HIGHER_NATIONAL_DEP("Higher National Diploma"),
    DIPLOMA("Diploma"),
    DEGREE("Degree"),
    MASTERS("Masters"),
    DOCTORATE("Doctorate"),
    PROFESSIONAL("Professional"),
    CERTIFICATE("Certificate");

    private final String message;

    AwardType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

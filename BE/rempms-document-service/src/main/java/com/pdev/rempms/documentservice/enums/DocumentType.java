package com.pdev.rempms.documentservice.enums;

public enum DocumentType {
    CV("Cv"),
    NIC("Nic"),
    JOB_POSTER("Job poster"),
    CERTIFICATE("Certificate");

    private final String message;

    DocumentType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

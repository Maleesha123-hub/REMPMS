package com.pdev.rempms.recruitmentservice.constants.enums;

public enum DocumentType {
    CV("Cv"),
    NIC("Nic"),
    JOB_POSTER("Job poster"),
    CERTIFICATE("Certificate");

    private final String value;

    DocumentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

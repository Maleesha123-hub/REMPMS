package com.pdev.rempms.locationservice.enums;

public enum CommonStatus {
    ACTIVE("Active"),
    INACTIVE("In_active"),
    DELETED("Deleted");

    private final String value;

    CommonStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

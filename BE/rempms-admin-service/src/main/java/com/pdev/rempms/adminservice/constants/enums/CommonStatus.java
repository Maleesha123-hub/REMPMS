package com.pdev.rempms.adminservice.constants.enums;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
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

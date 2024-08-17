package com.pdev.rempms.adminservice.constants.enums;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
public enum UserRoles {
    ADMINISTRATOR("ADMINISTRATOR"),
    RECRUITMENT_OFFICER("RECRUITMENT_OFFICER"),
    FINANCE_OFFICER("FINANCE_OFFICER"),
    CANDIDATE("CANDIDATE");

    private final String value;

    UserRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

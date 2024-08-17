package com.pdev.rempms.adminservice.constants.enums;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
public enum DocumentType {
    POSTS_IMG("POSTS_IMG"),
    USER_IMG("USER_IMG"),
    CV_DOCUMENT("CV_DOCUMENT"),
    CERTIFICATE("CERTIFICATE"),
    REPORT("REPORT");

    private final String value;

    DocumentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

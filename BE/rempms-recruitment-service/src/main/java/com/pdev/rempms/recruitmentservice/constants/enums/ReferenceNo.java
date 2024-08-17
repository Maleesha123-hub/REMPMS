package com.pdev.rempms.recruitmentservice.constants.enums;

public enum ReferenceNo {

    EMPLOYER_REF("EMP-ER"),

    JOB_VACANCY_REF("JOB-V");

    private final String name;

    ReferenceNo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

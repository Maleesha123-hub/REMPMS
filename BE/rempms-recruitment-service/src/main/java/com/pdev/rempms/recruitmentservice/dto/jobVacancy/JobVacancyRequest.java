package com.pdev.rempms.recruitmentservice.dto.jobVacancy;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JobVacancyRequest {
    private Integer id;
    private String description;
    private LocalDate closingDate;
    private boolean govtJob;
    private boolean walksInInterview;
    private boolean partTime;
    private String posterUrl;
    private Integer employerId;
    private Integer jobPositionId;
    private String jobVacancyRefNo;
}

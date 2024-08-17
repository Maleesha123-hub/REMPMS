package com.pdev.rempms.recruitmentservice.dto.jobVacancy;

import com.pdev.rempms.recruitmentservice.dto.employer.EmployerDTO;
import com.pdev.rempms.recruitmentservice.dto.jobPosition.JobPositionResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JobVacancyResponse {
    private Integer id;
    private String description;
    private LocalDate closingDate;
    private LocalDate openingDate;
    private boolean govtJob;
    private boolean walksInInterview;
    private boolean partTime;
    private boolean active;
    private String posterUrl;
    private String posterName;
    private Integer employerId;
    private EmployerDTO employer;
    private Integer jobPositionId;
    private String jobVacancyRefNo;
    private JobPositionResponseDTO jobPosition;
}

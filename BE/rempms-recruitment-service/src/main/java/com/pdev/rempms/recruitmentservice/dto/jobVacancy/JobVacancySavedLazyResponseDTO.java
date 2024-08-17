package com.pdev.rempms.recruitmentservice.dto.jobVacancy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobVacancySavedLazyResponseDTO {
    private Integer id;
    private String jobPosition;
    private String employer;
    private String employerNo;
    private boolean active;
    private String refNo;
    private String posterName;
}

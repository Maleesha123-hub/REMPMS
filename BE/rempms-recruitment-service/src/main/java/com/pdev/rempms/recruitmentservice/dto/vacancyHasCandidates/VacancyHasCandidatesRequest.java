package com.pdev.rempms.recruitmentservice.dto.vacancyHasCandidates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VacancyHasCandidatesRequest {
    private Integer id;
    private String name;
    private String description;
    private String email;
    private String cvUrl;
    private Integer candidateId;
    private Integer jobVacancyId;
}

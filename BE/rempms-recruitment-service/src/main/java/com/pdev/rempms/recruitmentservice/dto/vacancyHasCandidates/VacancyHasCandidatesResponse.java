package com.pdev.rempms.recruitmentservice.dto.vacancyHasCandidates;

import com.pdev.rempms.recruitmentservice.dto.candidate.CandidateDTO;
import com.pdev.rempms.recruitmentservice.dto.jobVacancy.JobVacancyResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyHasCandidatesResponse {
    private Integer id;
    private String name;
    private String description;
    private String email;
    private String cvUrl;
    private Integer candidateId;
    private CandidateDTO candidate;
    private Integer jobVacancyId;
    private JobVacancyResponse jobVacancy;
}

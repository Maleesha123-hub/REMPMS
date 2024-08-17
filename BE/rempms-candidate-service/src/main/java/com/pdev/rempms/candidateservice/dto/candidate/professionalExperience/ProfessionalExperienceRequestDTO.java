package com.pdev.rempms.candidateservice.dto.candidate.professionalExperience;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class ProfessionalExperienceRequestDTO {

    private Integer idProfessionalExperience;
    private String organization;
    private String designation;
    private String commencedDate;
    private String completionDate;
    private String description;
    private Integer idIndustry;
    private Integer idJobCategory;
    private Boolean stillWorking;

}

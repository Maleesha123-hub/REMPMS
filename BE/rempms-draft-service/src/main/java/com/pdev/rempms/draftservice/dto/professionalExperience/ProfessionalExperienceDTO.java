package com.pdev.rempms.draftservice.dto.professionalExperience;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class ProfessionalExperienceDTO {

    private String idProfessionalExperience;
    private String organization;
    private String designation;
    private String commencedDate;
    private String completionDate;
    private String description;
    private String idIndustry;
    private String idJobCategory;
    private Boolean stillWorking;

}

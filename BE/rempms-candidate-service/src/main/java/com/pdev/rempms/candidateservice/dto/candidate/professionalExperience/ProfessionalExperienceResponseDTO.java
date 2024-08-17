package com.pdev.rempms.candidateservice.dto.candidate.professionalExperience;

import com.pdev.rempms.candidateservice.dto.candidate.industry.IndustryDTO;
import com.pdev.rempms.candidateservice.dto.jobCategory.JobCategoryDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class ProfessionalExperienceResponseDTO {

    private Integer idProfessionalExperience;
    private String organization;
    private String designation;
    private String commencedDate;
    private String completionDate;
    private String description;

    private Integer idIndustry;
    private IndustryDTO industry;

    private Integer idJobCategory;
    private JobCategoryDTO jobCategory;

    private Boolean stillWorking;

}

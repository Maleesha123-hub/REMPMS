package com.pdev.rempms.candidateservice.dto.candidate.jobPreference;

import com.pdev.rempms.candidateservice.dto.candidate.industry.IndustryDTO;
import com.pdev.rempms.candidateservice.dto.communication.preferredCommunication.PreferredCommunicationDTO;
import com.pdev.rempms.candidateservice.dto.jobCategory.JobCategoryDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class JobPreferenceResponseDTO {
    private Integer idJobPreference;

    private Integer idJobCategory;
    private JobCategoryDTO jobCategory;

    private Integer idIndustry;
    private IndustryDTO industry;

    private String preference; // hardcoded values : 1,2,3,4,5
    private String remark;

    private Integer idPreferredCommunication;
    private PreferredCommunicationDTO preferredCommunication;

}

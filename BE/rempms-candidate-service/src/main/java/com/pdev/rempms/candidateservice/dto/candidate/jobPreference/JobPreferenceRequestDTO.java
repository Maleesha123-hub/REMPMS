package com.pdev.rempms.candidateservice.dto.candidate.jobPreference;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class JobPreferenceRequestDTO {
    private Integer idJobPreference;
    private Integer idJobCategory;
    private Integer idIndustry;
    private String preference; // hardcoded values : 1,2,3,4,5
    private String remark;
    private Integer idPreferredCommunication;
}

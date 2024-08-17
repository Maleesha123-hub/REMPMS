package com.pdev.rempms.draftservice.dto.jobPreference;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class JobPreferenceDTO {
    private String idJobPreference;
    private String idJobCategory;
    private String idIndustry;
    private String preference; // hardcoded values : 1,2,3,4,5
    private String remark;
    private String idPreferredCommunication;
    private String idCandidate;
}

package com.pdev.rempms.recruitmentservice.dto.candidate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateDTO {
    private Integer idCandidate;
    private String candidateNo;
    private Integer idUserAccount; // user-service
    private String commonStatus;
    private Boolean isVerify;
}

package com.pdev.rempms.candidateservice.dto.candidate;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class CandidateDTO {
    private Integer idCandidate;
    private String candidateNo;
    private Integer idUserAccount; // user-service
    private String commonStatus;
    private Boolean isVerify;

}

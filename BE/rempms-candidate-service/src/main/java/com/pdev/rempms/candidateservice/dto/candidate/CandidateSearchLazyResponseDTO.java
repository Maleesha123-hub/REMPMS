package com.pdev.rempms.candidateservice.dto.candidate;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CandidateSearchLazyResponseDTO {

    private Integer candidateId;
    private String candidateNo;
    private String fullName;
    private String nic;
    private String noticePeriod;
    private BigDecimal expectedSalary;

}

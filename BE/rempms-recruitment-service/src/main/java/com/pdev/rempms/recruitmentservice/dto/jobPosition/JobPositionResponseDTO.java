package com.pdev.rempms.recruitmentservice.dto.jobPosition;

import com.pdev.rempms.recruitmentservice.dto.candidate.industry.IndustryDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPositionResponseDTO {
    private Integer id;
    private String name;
    private Integer industryId;
    private IndustryDTO industry;
    private Boolean active;
}

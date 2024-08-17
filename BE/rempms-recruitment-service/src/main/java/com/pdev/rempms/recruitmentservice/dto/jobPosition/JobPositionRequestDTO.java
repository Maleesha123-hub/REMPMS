package com.pdev.rempms.recruitmentservice.dto.jobPosition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPositionRequestDTO {
    private Integer id;
    private String name;
    private Integer industryId;
}

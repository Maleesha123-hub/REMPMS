package com.pdev.rempms.candidateservice.dto.candidate.research;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class ResearchRequestDTO {
    private Integer idResearch;
    private String commencedDate;
    private String completionDate;
    private String description;
    private Integer idResearchArea;
}

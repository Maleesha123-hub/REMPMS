package com.pdev.rempms.draftservice.dto.research;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class ResearchDTO {
    private String idResearch;
    private String commencedDate;
    private String completionDate;
    private String description;
    private String idResearchArea;
    private String idCandidate;
}

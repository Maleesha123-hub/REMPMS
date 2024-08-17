package com.pdev.rempms.candidateservice.dto.candidate.research;

import com.pdev.rempms.candidateservice.dto.candidate.areaOfStudy.AreaOfStudyDTO;
import com.pdev.rempms.candidateservice.dto.researchArea.ResearchAreaDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class ResearchResponseDTO {
    private Integer idResearch;
    private String commencedDate;
    private String completionDate;
    private String description;

    private Integer idResearchArea;
    private ResearchAreaDTO researchArea;

}

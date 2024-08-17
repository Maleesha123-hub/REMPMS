package com.pdev.rempms.candidateservice.mapper.researchArea;

import com.pdev.rempms.candidateservice.dto.researchArea.ResearchAreaDTO;
import com.pdev.rempms.candidateservice.model.candidate.research.ResearchArea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResearchAreaMapper {

    public ResearchAreaDTO toDto(ResearchAreaDTO dto, ResearchArea researchArea) {

        dto.setId(researchArea.getId());
        dto.setDescription(researchArea.getDescription());
        dto.setName(researchArea.getName());

        return dto;

    }

}

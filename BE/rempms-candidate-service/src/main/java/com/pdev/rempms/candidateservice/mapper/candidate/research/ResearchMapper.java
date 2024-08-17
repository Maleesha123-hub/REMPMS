package com.pdev.rempms.candidateservice.mapper.candidate.research;

import com.pdev.rempms.candidateservice.dto.candidate.research.ResearchRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.research.ResearchResponseDTO;
import com.pdev.rempms.candidateservice.dto.researchArea.ResearchAreaDTO;
import com.pdev.rempms.candidateservice.mapper.researchArea.ResearchAreaMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.research.Research;
import com.pdev.rempms.candidateservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/09
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ResearchMapper {

    private final ResearchAreaMapper researchAreaMapper;

    /**
     * This method is allowed to convert model to dto
     *
     * @param dto      {@link ResearchResponseDTO}
     * @param research {@link Research} - entity
     * @return {@link ResearchRequestDTO}
     * @author @maleeshasa
     */
    public ResearchResponseDTO modelToDto(ResearchResponseDTO dto, Research research) {
        log.info("ResearchMapper.modelToDto() => started.");

        dto.setIdResearch(research.getId());
        dto.setCommencedDate(DateTimeUtil.getFormattedDate(research.getCommencedDate()));
        dto.setCompletionDate(DateTimeUtil.getFormattedDate(research.getCompletionDate()));
        dto.setDescription(research.getDescription());

        dto.setIdResearchArea(research.getResearchArea() == null ? null :
                research.getResearchArea().getId());
        dto.setResearchArea(research.getResearchArea() == null ? null :
                researchAreaMapper.toDto(new ResearchAreaDTO(), research.getResearchArea()));

        log.info("ResearchMapper.modelToDto() => ended.");
        return dto;

    }

    /**
     * This method is allowed to convert model to dto
     *
     * @param researchRequestDTO {@link ResearchRequestDTO} - dto
     * @return {@link Research}
     * @author @maleeshasa
     */
    public Research dtoToModel(Research research, ResearchRequestDTO researchRequestDTO, Candidate candidate) {
        log.info("ResearchMapper.dtoToModel() => started.");

        research.setCandidate(candidate);
        research.setDescription(researchRequestDTO.getDescription());
        research.setCommencedDate(DateTimeUtil.getFormattedDate(researchRequestDTO.getCommencedDate()));
        research.setCompletionDate(DateTimeUtil.getFormattedDate(researchRequestDTO.getCompletionDate()));

        log.info("ResearchMapper.dtoToModel() => ended.");
        return research;
    }

}

package com.pdev.rempms.candidateservice.mapper.candidate.industry;

import com.pdev.rempms.candidateservice.dto.candidate.industry.IndustryDTO;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.Industry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @maleeshasa
 * @Date 2024/03/07
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class IndustryMapper {

    public IndustryDTO toDto(IndustryDTO dto, Industry industry) {
        log.info("IndustryMapper.toDto() => started.");

        dto.setId(industry.getId());
        dto.setName(industry.getName());
        dto.setDescription(industry.getDescription());

        log.info("IndustryMapper.toDto() => ended.");

        return dto;

    }

}

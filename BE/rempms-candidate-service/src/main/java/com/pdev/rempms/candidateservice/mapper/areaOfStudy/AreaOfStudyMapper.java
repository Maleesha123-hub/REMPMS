package com.pdev.rempms.candidateservice.mapper.areaOfStudy;

import com.pdev.rempms.candidateservice.dto.candidate.areaOfStudy.AreaOfStudyDTO;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.AreaOfStudy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Component
@Slf4j
public class AreaOfStudyMapper {

    public AreaOfStudyDTO toDto(AreaOfStudyDTO dto,
                                AreaOfStudy areaOfStudy) {

        dto.setId(areaOfStudy.getId());
        dto.setDescription(areaOfStudy.getDescription());
        dto.setName(areaOfStudy.getName());

        return dto;
    }

}

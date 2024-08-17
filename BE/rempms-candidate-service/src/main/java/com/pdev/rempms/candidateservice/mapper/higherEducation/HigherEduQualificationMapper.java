package com.pdev.rempms.candidateservice.mapper.higherEducation;

import com.pdev.rempms.candidateservice.dto.candidate.higherEducation.HigherEduQualificationDTO;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEduQualification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HigherEduQualificationMapper {

    public HigherEduQualificationDTO toDto(HigherEduQualificationDTO dto,
                                           HigherEduQualification higherEduQualification) {
        log.info("HigherEduQualificationMapper -> toDto() => started!");

        dto.setId(higherEduQualification.getId());
        dto.setAwardType(higherEduQualification.getAwardType());
        dto.setName(higherEduQualification.getName());
        dto.setDescription(higherEduQualification.getDescription());

        log.info("HigherEduQualificationMapper -> toDto() => ended!");
        return dto;

    }

}

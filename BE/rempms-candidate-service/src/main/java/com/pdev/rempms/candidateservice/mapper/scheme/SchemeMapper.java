package com.pdev.rempms.candidateservice.mapper.scheme;

import com.pdev.rempms.candidateservice.dto.scheme.SchemeDTO;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.Scheme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SchemeMapper {

    public SchemeDTO toDto(SchemeDTO dto, Scheme scheme) {

        dto.setId(scheme.getId());
        dto.setSchemeName(scheme.getSchemeName());
        dto.setSchoolEduQualification(scheme.getSchoolEduQualification());

        return dto;

    }

}

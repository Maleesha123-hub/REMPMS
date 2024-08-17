package com.pdev.rempms.candidateservice.mapper.subject;

import com.pdev.rempms.candidateservice.dto.scheme.SchemeDTO;
import com.pdev.rempms.candidateservice.dto.subject.SubjectDTO;
import com.pdev.rempms.candidateservice.mapper.scheme.SchemeMapper;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SubjectMapper {

    private final SchemeMapper schemeMapper;

    public SubjectDTO toDto(SubjectDTO dto, Subject subject) {

        dto.setId(subject.getId());
        dto.setScheme(subject.getScheme() == null ? null : schemeMapper.toDto(new SchemeDTO(), subject.getScheme()));
        dto.setSchoolEduQualification(subject.getSchoolEduQualification());
        dto.setSubjectName(subject.getSubjectName());

        return dto;

    }

}

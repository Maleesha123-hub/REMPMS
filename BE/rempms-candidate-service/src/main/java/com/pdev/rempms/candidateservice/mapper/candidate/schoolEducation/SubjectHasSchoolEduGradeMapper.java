package com.pdev.rempms.candidateservice.mapper.candidate.schoolEducation;

import com.pdev.rempms.candidateservice.dto.candidate.schoolEducation.SubjectHasSchoolEduGradeDTO;
import com.pdev.rempms.candidateservice.dto.subject.SubjectDTO;
import com.pdev.rempms.candidateservice.mapper.subject.SubjectMapper;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.SchoolEducation;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.Subject;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.SubjectHasSchoolEduGrade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/05
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SubjectHasSchoolEduGradeMapper {

    private final SubjectMapper subjectMapper;

    /**
     * This method is allowed to map subject has grade to model
     *
     * @param hasSchoolEduGrade {@link SubjectHasSchoolEduGrade} - subject has grade model
     * @param schoolEducation   {@link SchoolEducation} - school education model
     * @param subject           {@link Subject} -  subject of subject has grade
     * @param grade             {@link String} -  grade of subject has grade
     * @return {@link SubjectHasSchoolEduGrade} - mapped model
     */
    public SubjectHasSchoolEduGrade toEntity(SubjectHasSchoolEduGrade hasSchoolEduGrade, SchoolEducation schoolEducation,
                                             Subject subject, String grade) {
        log.info("SubjectHasSchoolEduGradeMapper -> toEntity() => started!");

        hasSchoolEduGrade.setSubject(subject);
        hasSchoolEduGrade.setGrade(grade);
        hasSchoolEduGrade.setSchoolEducation(schoolEducation);

        log.info("SubjectHasSchoolEduGradeMapper -> toEntity() => ended!");
        return hasSchoolEduGrade;
    }

    public SubjectHasSchoolEduGradeDTO toDto(SubjectHasSchoolEduGradeDTO dto, SubjectHasSchoolEduGrade subjectHasSchoolEduGrade) {
        log.info("SubjectHasSchoolEduGradeMapper -> toDto() => started!");

        dto.setIdSubjectHasSchoolEduGradeDTO(subjectHasSchoolEduGrade.getId());
        dto.setGrade(subjectHasSchoolEduGrade.getGrade());

        dto.setIdSubject(subjectHasSchoolEduGrade.getSubject() == null ? null : subjectHasSchoolEduGrade.getSubject().getId());
        dto.setSubject(subjectHasSchoolEduGrade.getSubject() == null ? null :
                subjectMapper.toDto(new SubjectDTO(), subjectHasSchoolEduGrade.getSubject()));

        log.info("SubjectHasSchoolEduGradeMapper -> toDto() => ended!");
        return dto;

    }

}

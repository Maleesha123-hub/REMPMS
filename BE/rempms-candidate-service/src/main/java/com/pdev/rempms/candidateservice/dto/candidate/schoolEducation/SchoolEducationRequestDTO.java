package com.pdev.rempms.candidateservice.dto.candidate.schoolEducation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class SchoolEducationRequestDTO {

    private Integer idSchoolEducation;
    private String schoolEduQualification; //enum : school qualification
    private String school; //school/institute
    private String achievedOn;
    private Integer idLanguage; //communication-service //medium
    private Integer idCountry; //location-service
    private String description;
    private Integer idScheme;
    private List<SubjectHasSchoolEduGradeDTO> subjectHasSchoolEduGrades;

}

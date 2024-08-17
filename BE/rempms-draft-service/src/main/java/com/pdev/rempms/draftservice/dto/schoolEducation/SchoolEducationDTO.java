package com.pdev.rempms.draftservice.dto.schoolEducation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class SchoolEducationDTO {

    private String idSchoolEducation;

    private String schoolEduQualification; //enum : school qualification

    private String school; //school/institute

    private String achievedOn;

    private String idLanguage; //communication-service //medium

    private String idCountry; //location-service

    private String description;

    private String idScheme;

    private String schemeName;

    private List<SubjectHasSchoolEduGradeDTO> subjectHasSchoolEduGrades;

}

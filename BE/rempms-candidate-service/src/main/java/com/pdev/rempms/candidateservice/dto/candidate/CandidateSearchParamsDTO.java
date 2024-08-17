package com.pdev.rempms.candidateservice.dto.candidate;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class CandidateSearchParamsDTO {

    // General
    private String candidateNo; // General
    private String noticePeriod; // General
    private Integer ageGraterThan; // General
    private Integer ageLessThan; // General
    private Integer preferredJobLocationId;

    // Education
    // Higher education
    private Integer areaOfStudyId;
    private Integer higherEduQualificationId;
    private String result;

    // School qualification
    private String schoolQualification;
    private Integer mediumId; // medium (language)
    private Integer schemeId;
    private List<HashMap<Integer, String>> subjectsAndGrades;

    // Professional experiences
    private Integer industryId;
    private Integer jobCategoryId;
    private String designation; // like query
    private Boolean stillWorking;
    private Integer workingExperience;

    // Language proficiency
    private Integer languageId;
    private String readingProficiency;
    private String spokenProficiency;
    private String writingProficiency;

}

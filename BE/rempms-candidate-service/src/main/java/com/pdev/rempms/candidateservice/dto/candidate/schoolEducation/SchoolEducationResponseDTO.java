package com.pdev.rempms.candidateservice.dto.candidate.schoolEducation;

import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.dto.scheme.SchemeDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class SchoolEducationResponseDTO {

    private Integer idSchoolEducation;
    private String schoolEduQualification; //enum : school qualification
    private String school; //school/institute
    private String achievedOn;

    private Integer idLanguage; //communication-service //medium
    private LanguageDTO language;

    private Integer idCountry; //location-service
    private CountryDTO country;

    private String description;

    private Integer idScheme;
    private SchemeDTO scheme;

    private List<SubjectHasSchoolEduGradeDTO> subjectHasSchoolEduGrades;

}

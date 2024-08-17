package com.pdev.rempms.candidateservice.dto.candidate.higherEducation;

import com.pdev.rempms.candidateservice.dto.candidate.areaOfStudy.AreaOfStudyDTO;
import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class HigherEducationResponseDTO {

    private Integer idHigherEducation;
    private String instituteOfStudy;
    private String affiliatedInstitute;
    private String awardType; //enum
    private String description;

    private Integer idHigherEduQualification;
    private HigherEduQualificationDTO higherEduQualification;

    private Integer idAreaOfStudy;
    private AreaOfStudyDTO areaOfStudy;

    private String commencedDate;
    private String completionDate;
    private String studentId;
    private String result;

    private Integer idCountry; //location-service
    private CountryDTO country;

    private Integer idLanguage; //communication-service //medium
    private LanguageDTO language;

}

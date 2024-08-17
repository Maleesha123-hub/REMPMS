package com.pdev.rempms.candidateservice.dto.candidate.higherEducation;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class HigherEducationRequestDTO {

    private Integer idHigherEducation;
    private String instituteOfStudy;
    private String affiliatedInstitute;
    private String awardType; //enum
    private Integer idHigherEduQualification;
    private Integer idAreaOfStudy;
    private String commencedDate;
    private String completionDate;
    private String studentId;
    private String result;
    private Integer idCountry; //location-service
    private Integer idLanguage; //communication-service //medium
    private String description;

}

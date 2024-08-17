package com.pdev.rempms.draftservice.dto.higherEducation;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class HigherEducationDTO {

    private String idHigherEducation;
    private String instituteOfStudy;
    private String affiliatedInstitute;
    private String awardType; //enum
    private String idHigherEduQualification;
    private String idAreaOfStudy;
    private String commencedDate;
    private String completionDate;
    private String studentId;
    private String result;
    private String idCountry; //location-service
    private String idLanguage; //communication-service //medium
    private String description;

}

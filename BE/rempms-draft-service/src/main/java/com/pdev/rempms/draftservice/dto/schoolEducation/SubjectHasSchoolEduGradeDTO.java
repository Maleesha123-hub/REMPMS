package com.pdev.rempms.draftservice.dto.schoolEducation;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class SubjectHasSchoolEduGradeDTO {

    private String idSubjectHasSchoolEduGradeDTO;

    private String idSubject;

    private String subject;

    private String idSchoolEducation;

    private String grade; //enum : grade

}

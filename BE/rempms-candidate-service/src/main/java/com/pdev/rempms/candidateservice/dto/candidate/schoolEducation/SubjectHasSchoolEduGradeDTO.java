package com.pdev.rempms.candidateservice.dto.candidate.schoolEducation;

import com.pdev.rempms.candidateservice.dto.subject.SubjectDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class SubjectHasSchoolEduGradeDTO {

    private Integer idSubjectHasSchoolEduGradeDTO;

    private Integer idSubject;
    private SubjectDTO subject;

    private Integer idSchoolEducation;
    private String grade; //enum : grade

}

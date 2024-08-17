package com.pdev.rempms.candidateservice.dto.subject;

import com.pdev.rempms.candidateservice.dto.scheme.SchemeDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectDTO {

    private Integer id;

    private String subjectName;

    private String schoolEduQualification; //enum - school qualification - OL/AL

    private SchemeDTO scheme;

}

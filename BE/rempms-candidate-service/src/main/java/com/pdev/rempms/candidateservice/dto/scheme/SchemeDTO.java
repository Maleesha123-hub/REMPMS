package com.pdev.rempms.candidateservice.dto.scheme;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchemeDTO {

    private Integer id;

    private String schemeName;

    private String schoolEduQualification; //enum - school qualification

}

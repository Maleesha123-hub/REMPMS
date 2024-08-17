package com.pdev.rempms.candidateservice.dto.candidate.languageProficiency;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class LanguageProficiencyRequestDTO {
    private Integer idLanguageProficiency;
    private Integer idLanguage; //communication-service
    private String spoken; //enum : proficiency
    private String reading; //enum : proficiency
    private String writing; //enum : proficiency
}

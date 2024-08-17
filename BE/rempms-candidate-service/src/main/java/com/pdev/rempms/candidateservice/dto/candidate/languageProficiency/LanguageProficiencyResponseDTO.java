package com.pdev.rempms.candidateservice.dto.candidate.languageProficiency;

import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class LanguageProficiencyResponseDTO {
    private Integer idLanguageProficiency;

    private Integer idLanguage; //communication-service
    private LanguageDTO language;

    private String spoken; //enum : proficiency
    private String reading; //enum : proficiency
    private String writing; //enum : proficiency
}

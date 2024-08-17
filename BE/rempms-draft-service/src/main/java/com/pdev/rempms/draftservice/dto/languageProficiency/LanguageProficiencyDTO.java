package com.pdev.rempms.draftservice.dto.languageProficiency;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class LanguageProficiencyDTO {
    private String idLanguageProficiency;
    private String idLanguage; //communication-service
    private String spoken; //enum : proficiency
    private String reading; //enum : proficiency
    private String writing; //enum : proficiency
    private String idCandidate;
}

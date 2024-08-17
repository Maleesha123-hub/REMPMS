package com.pdev.rempms.candidateservice.dto.communication;

import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class CommunicationInformationResponseDTO { //communication-service
    private Integer idCommunicationInformation;
    private String phoneNo;
    private String mobileNo;
    private String email;

    private Integer commInfoIdLanguage;
    private LanguageDTO language;

    private Integer commInfoIdPreferredCommunication;
    private CommunicationInformationResponseDTO communicationInformation;

}

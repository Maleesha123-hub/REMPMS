package com.pdev.rempms.candidateservice.dto.communication;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class CommunicationInformationRequestDTO { //communication-service
    private String phoneNo;
    private String mobileNo;
    private String email;
    private Integer commInfoIdLanguage;
    private Integer commInfoIdPreferredCommunication;
    private Integer idCommunicationInformation;
}

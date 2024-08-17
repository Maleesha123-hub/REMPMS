package com.pdev.rempms.communicationservice.dto.communicationInformation;

import com.pdev.rempms.communicationservice.dto.language.LanguageDTO;
import com.pdev.rempms.communicationservice.dto.preferredCommunication.PreferredCommunicationDTO;
import lombok.Data;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Data
public class CommunicationInformationDTO {
    private String idCommunicationInformation;
    private String phoneNo;
    private String mobileNo;
    private String email;
    private String commonStatus;
    private LanguageDTO language;
    private PreferredCommunicationDTO preferredCommunication;
    private String idLanguage;
    private String idPreferredCommunication;
}

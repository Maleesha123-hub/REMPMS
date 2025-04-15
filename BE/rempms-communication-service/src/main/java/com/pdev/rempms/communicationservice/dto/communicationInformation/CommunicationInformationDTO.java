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
    private String customerName;
    private String phoneNo;
    private String mobileNo;
    private String email;
    private String commInfoCommonStatus;
    private LanguageDTO language;
    private PreferredCommunicationDTO preferredCommunication;
    private String commInfoIdPreferredCommunication;
    private String commInfoIdLanguage;
}

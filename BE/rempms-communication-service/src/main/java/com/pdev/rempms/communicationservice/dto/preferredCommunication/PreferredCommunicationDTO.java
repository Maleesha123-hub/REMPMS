package com.pdev.rempms.communicationservice.dto.preferredCommunication;

import lombok.Data;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Data
public class PreferredCommunicationDTO {
    private String idPreferredCommunication;
    private String preferredCommunicationMethod;
    private String preferredCommunicationDescription;
    private String preferredCommunicationCommonStatus;
}

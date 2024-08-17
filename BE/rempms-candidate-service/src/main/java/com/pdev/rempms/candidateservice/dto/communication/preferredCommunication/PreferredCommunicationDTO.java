package com.pdev.rempms.candidateservice.dto.communication.preferredCommunication;

import lombok.Getter;
import lombok.Setter;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Getter
@Setter
public class PreferredCommunicationDTO {

    private Integer idPreferredCommunication;
    private String preferredCommunicationMethod;
    private String preferredCommunicationDescription;
    private String preferredCommunicationCommonStatus;

}

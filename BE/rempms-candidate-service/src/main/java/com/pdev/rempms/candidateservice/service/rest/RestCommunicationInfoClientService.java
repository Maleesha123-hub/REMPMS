package com.pdev.rempms.candidateservice.service.rest;

import com.pdev.rempms.candidateservice.dto.candidate.personalDetail.PersonalDetailRequestDTO;
import com.pdev.rempms.candidateservice.dto.communication.CommunicationInformationRequestDTO;
import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.dto.communication.preferredCommunication.PreferredCommunicationDTO;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
public interface RestCommunicationInfoClientService {

    /**
     * This method is allowed to save communication details of candidate
     *
     * @param dto - {@link PersonalDetailRequestDTO} - personal details with communication details
     * @return {@link CommunicationInformationRequestDTO} - saved communication details response
     */
    CommunicationInformationRequestDTO saveCommunicationInfo(PersonalDetailRequestDTO dto);

    /**
     * This method is allowed to get language by id
     *
     * @param idLanguage {@link Integer} - language id
     * @return {@link LanguageDTO} - language response
     * @author @Maleesha99
     */
    LanguageDTO getActiveLanguageById(Integer idLanguage);

    /**
     * This method is allowed to get preferred communication by id
     *
     * @param idPreferredCommunication {@link Long}
     * @return {@link PreferredCommunicationDTO} - preferred communication response
     * @author @Maleesha99
     */
    PreferredCommunicationDTO getActivePreferredCommunicationById(Integer idPreferredCommunication);

}

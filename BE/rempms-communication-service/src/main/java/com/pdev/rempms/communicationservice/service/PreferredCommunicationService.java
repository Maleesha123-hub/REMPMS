package com.pdev.rempms.communicationservice.service;

import com.pdev.rempms.communicationservice.dto.preferredCommunication.PreferredCommunicationDTO;
import com.pdev.rempms.communicationservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
public interface PreferredCommunicationService {

    /**
     * save or update preferred communication
     *
     * @param dto - Preferred communication saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdatePreferredCommunication(PreferredCommunicationDTO dto);

    /**
     * Get active preferred communication by id
     *
     * @param id - preferred communication id
     * @return - Active preferred communication data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActivePreferredCommunicationById(Long id);

    /**
     * Get all active preferred communications
     *
     * @return - All Active preferred communications data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActivePreferredCommunications();

    /**
     * Delete preferred communication by id
     *
     * @param id - preferred communication id
     * @return - preferred communication deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deletePreferredCommunicationById(Long id);
}

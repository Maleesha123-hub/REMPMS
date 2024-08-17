package com.pdev.rempms.communicationservice.service;

import com.pdev.rempms.communicationservice.dto.communicationInformation.CommunicationInformationDTO;
import com.pdev.rempms.communicationservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
public interface CommunicationInformationService {

    /**
     * save or update communication information
     *
     * @param dto - Communication information saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdateCommunicationInformation(CommunicationInformationDTO dto);

    /**
     * Get active communication information by id
     *
     * @param id - communication information id
     * @return - Active communication information data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActiveCommunicationInformationById(Long id);

    /**
     * Get all active communication informations
     *
     * @return - All Active communication informations data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActiveCommunicationInformation();

    /**
     * Delete communication information by id
     *
     * @param id - communication information id
     * @return - communication information deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deleteCommunicationInformationById(Long id);
}

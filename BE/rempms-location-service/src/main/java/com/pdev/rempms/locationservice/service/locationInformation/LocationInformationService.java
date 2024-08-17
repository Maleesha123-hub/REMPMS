package com.pdev.rempms.locationservice.service.locationInformation;

import com.pdev.rempms.locationservice.dto.locationInformation.LocationInformationDTO;
import com.pdev.rempms.locationservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
public interface LocationInformationService {

    /**
     * save or update location information
     *
     * @param dto - location information saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdateLocationInformation(LocationInformationDTO dto);

    /**
     * Get active location information by id
     *
     * @param idLocationInformation - location information id
     * @return - Active district data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActiveLocationInformationById(Long idLocationInformation);

    /**
     * Get all active location infos
     *
     * @return - All Active location info data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActiveLocationInformation();

    /**
     * Delete location info by location info id
     *
     * @param idLocationInformation - idLocationInformation id
     * @return - idLocationInformation deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deleteLocationInformationById(Long idLocationInformation);
}

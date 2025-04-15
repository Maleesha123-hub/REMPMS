package com.pdev.rempms.locationservice.controller;

import com.pdev.rempms.locationservice.dto.locationInformation.LocationInformationDTO;
import com.pdev.rempms.locationservice.service.locationInformation.LocationInformationService;
import com.pdev.rempms.locationservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author maleeshasa
 * @Date 29/12/2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/location/v1/locationInformation")
public class LocationInformationController {

    private final LocationInformationService locationInformationService;

    /**
     * save or update location information
     *
     * @param dto - location information saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdateLocationInformation(@RequestBody LocationInformationDTO dto) {
        return locationInformationService.saveUpdateLocationInformation(dto);
    }

    /**
     * Get active location information by id
     *
     * @param idLocationInformation - location information id
     * @return - Active district data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getById/{idLocationInformation}")
    public ResponseEntity<CommonResponse> getActiveLocationInformationById(@PathVariable Long idLocationInformation) {
        return locationInformationService.getActiveLocationInformationById(idLocationInformation);
    }

    /**
     * Get all active location infos
     *
     * @return - All Active location info data.
     * @author maleeshasa
     */
    @GetMapping(value ="/getAllActive")
    public ResponseEntity<CommonResponse> getAllActiveLocationInformation() {
        return locationInformationService.getAllActiveLocationInformation();
    }

    /**
     * Delete location info by location info id
     *
     * @param idLocationInformation - idLocationInformation id
     * @return - idLocationInformation deleted success info.
     * @author maleeshasa
     */
    @GetMapping(value ="/deleteById/{idLocationInformation}")
    public ResponseEntity<CommonResponse> deleteLocationInformationById(@PathVariable Long idLocationInformation) {
        return locationInformationService.deleteLocationInformationById(idLocationInformation);
    }
}

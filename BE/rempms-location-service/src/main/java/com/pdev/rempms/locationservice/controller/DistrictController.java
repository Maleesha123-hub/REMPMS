package com.pdev.rempms.locationservice.controller;

import com.pdev.rempms.locationservice.dto.district.DistrictDTO;
import com.pdev.rempms.locationservice.service.district.DistrictService;
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
@RequestMapping("api/location/v1/district")
public class DistrictController {

    private final DistrictService districtService;

    /**
     * save or update district
     *
     * @param dto - district saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdateDistrict(@RequestBody DistrictDTO dto) {
        return districtService.saveUpdateDistrict(dto);
    }

    /**
     * Get active district by id
     *
     * @param idDistrict - district id
     * @return - Active district data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getById/{idDistrict}")
    public ResponseEntity<CommonResponse> getActiveDistrictById(@PathVariable Long idDistrict) {
        return districtService.getActiveDistrictById(idDistrict);
    }

    /**
     * Get all active districts
     *
     * @return - All Active districts data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActiveDistricts() {
        return districtService.getAllActiveDistricts();
    }

    /**
     * Delete district by district id
     *
     * @param idDistrict - district id
     * @return - District deleted success info.
     * @author maleeshasa
     */
    @GetMapping(value = "/deleteById/{idDistrict}")
    public ResponseEntity<CommonResponse> deleteDistrictById(@PathVariable Long idDistrict) {
        return districtService.deleteDistrictById(idDistrict);
    }

    /**
     * Get districts by country id and province id
     *
     * @param idCountry  - country id
     * @param idProvince - province id
     * @return - districts success info.
     * @author maleeshasa
     */
    @GetMapping(value = "/getDistrictsByCountryIdAndProvinceId/{idProvince}/{idCountry}")
    public ResponseEntity<CommonResponse> getDistrictsByCountryIdAndProvinceId(@PathVariable Long idProvince,
                                                                               @PathVariable Long idCountry) {
        return districtService.getDistrictsByCountryIdAndProvinceId(idProvince, idCountry);
    }
}

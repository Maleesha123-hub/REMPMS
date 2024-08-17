package com.pdev.rempms.locationservice.service.district;

import com.pdev.rempms.locationservice.dto.district.DistrictDTO;
import com.pdev.rempms.locationservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
public interface DistrictService {

    /**
     * save or update district
     *
     * @param dto - district saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdateDistrict(DistrictDTO dto);

    /**
     * Get active district by id
     *
     * @param idDistrict - district id
     * @return - Active district data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActiveDistrictById(Long idDistrict);

    /**
     * Get all active districts
     *
     * @return - All Active districts data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActiveDistricts();

    /**
     * Delete district by district id
     *
     * @param idDistrict - district id
     * @return - District deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deleteDistrictById(Long idDistrict);

    /**
     * Get districts by country id and province id
     *
     * @param idCountry  - country id
     * @param idProvince - province id
     * @return - districts success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getDistrictsByCountryIdAndProvinceId(Long idProvince, Long idCountry);
}

package com.pdev.rempms.locationservice.service.province;

import com.pdev.rempms.locationservice.dto.province.ProvinceDTO;
import com.pdev.rempms.locationservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
public interface ProvinceService {

    /**
     * save or update province
     *
     * @param dto - province saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdateProvince(ProvinceDTO dto);

    /**
     * Get active province by id
     *
     * @param idProvince - province id
     * @return - Active province data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActiveProvinceById(Long idProvince);

    /**
     * Get all active provinces
     *
     * @return - All Active provinces data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActiveProvinces();

    /**
     * Delete province by province id
     *
     * @param idProvince - province id
     * @return - Province deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deleteProvinceById(Long idProvince);

    /**
     * Get provinces by country id
     *
     * @param idCountry - country id
     * @return - Provinces.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getProvincesByCountryId(Long idCountry);
}

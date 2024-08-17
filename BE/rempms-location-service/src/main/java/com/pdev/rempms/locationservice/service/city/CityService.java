package com.pdev.rempms.locationservice.service.city;

import com.pdev.rempms.locationservice.dto.city.CityDTO;
import com.pdev.rempms.locationservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
public interface CityService {

    /**
     * save or update city
     *
     * @param dto - city saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdateCity(CityDTO dto);

    /**
     * Get active city by id
     *
     * @param idCity - city id
     * @return - Active city data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActiveCityById(Long idCity);

    /**
     * Get all active cities
     *
     * @return - All Active cities data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActiveCities();

    /**
     * Delete city by city id
     *
     * @param idCity - city id
     * @return - City deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deleteCityById(Long idCity);

    /**
     * Get cities by country, province and district
     *
     * @param idCountry  - country id
     * @param idProvince - province id
     * @param idDistrict - district id
     * @return - Cities  success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getByIdCountryProvinceAndDistrict(Long idCountry, Long idProvince, Long idDistrict);
}

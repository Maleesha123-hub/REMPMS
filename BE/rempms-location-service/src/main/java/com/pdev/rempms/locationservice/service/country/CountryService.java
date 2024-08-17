package com.pdev.rempms.locationservice.service.country;

import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import com.pdev.rempms.locationservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
public interface CountryService {

    /**
     * save or update country
     *
     * @param dto - country saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdateCountry(CountryDTO dto);

    /**
     * Get active country by id
     *
     * @param idCountry - country id
     * @return - Active country data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActiveCountryById(Long idCountry);

    /**
     * Get all active countries
     *
     * @return - All Active countries data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActiveCountries();

    /**
     * Delete country by country id
     *
     * @param idCountry - country id
     * @return - Country deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deleteCountryById(Long idCountry);
}

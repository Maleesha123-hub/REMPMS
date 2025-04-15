package com.pdev.rempms.locationservice.controller;

import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import com.pdev.rempms.locationservice.service.country.CountryService;
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
@RequestMapping("/api/location/v1/country")
public class CountryController {

    private final CountryService countryService;

    /**
     * save or update country
     *
     * @param dto - country saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdateCountry(@RequestBody CountryDTO dto) {
        return countryService.saveUpdateCountry(dto);
    }

    /**
     * Get active country by id
     *
     * @param idCountry - country id
     * @return - Active country data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getById/{idCountry}")
    public ResponseEntity<CommonResponse> getActiveCountryById(@PathVariable Integer idCountry) {
        return countryService.getActiveCountryById(Long.valueOf(idCountry));
    }

    /**
     * Get all active countries
     *
     * @return - All Active countries data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActiveCountries() {
        return countryService.getAllActiveCountries();
    }

    /**
     * Delete country by country id
     *
     * @param idCountry - country id
     * @return - Country deleted success info.
     * @author maleeshasa
     */
    @GetMapping(value = "/deleteById/{idCountry}")
    public ResponseEntity<CommonResponse> deleteCountryById(@PathVariable Long idCountry) {
        return countryService.deleteCountryById(idCountry);
    }
}

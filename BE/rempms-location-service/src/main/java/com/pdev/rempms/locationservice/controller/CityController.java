package com.pdev.rempms.locationservice.controller;

import com.pdev.rempms.locationservice.dto.city.CityDTO;
import com.pdev.rempms.locationservice.service.city.CityService;
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
@RequestMapping("api/location/v1/city")
public class CityController {

    private final CityService cityService;

    /**
     * save or update city
     *
     * @param dto - city saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdateCity(@RequestBody CityDTO dto) {
        return cityService.saveUpdateCity(dto);
    }

    /**
     * Get active city by id
     *
     * @param idCity - city id
     * @return - Active city data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getById/{idCity}")
    public ResponseEntity<CommonResponse> getActiveCityById(@PathVariable Long idCity) {
        return cityService.getActiveCityById(idCity);
    }

    /**
     * Get all active cities
     *
     * @return - All Active cities data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActiveCities() {
        return cityService.getAllActiveCities();
    }

    /**
     * Delete city by city id
     *
     * @param idCity - city id
     * @return - City deleted success info.
     * @author maleeshasa
     */
    @GetMapping(value = "/deleteById/{idCity}")
    public ResponseEntity<CommonResponse> deleteCityById(@PathVariable Long idCity) {
        return cityService.deleteCityById(idCity);
    }

    /**
     * Get cities by country, province and district
     *
     * @param idCountry  - country id
     * @param idProvince - province id
     * @param idDistrict - district id
     * @return - Cities  success info.
     * @author maleeshasa
     */
    @GetMapping(value = "/getByIdCountryProvinceAndDistrict/{idCountry}/{idProvince}/{idDistrict}")
    public ResponseEntity<CommonResponse> getByIdCountryProvinceAndDistrict(@PathVariable Long idCountry,
                                                                            @PathVariable Long idProvince,
                                                                            @PathVariable Long idDistrict) {
        return cityService.getByIdCountryProvinceAndDistrict(idCountry, idProvince, idDistrict);
    }
}

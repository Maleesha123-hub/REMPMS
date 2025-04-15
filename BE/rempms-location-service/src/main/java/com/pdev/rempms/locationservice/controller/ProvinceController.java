package com.pdev.rempms.locationservice.controller;

import com.pdev.rempms.locationservice.dto.province.ProvinceDTO;
import com.pdev.rempms.locationservice.service.province.ProvinceService;
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
@RequestMapping("api/location/v1/province")
public class ProvinceController {

    private final ProvinceService provinceService;

    /**
     * save or update province
     *
     * @param dto - province saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdateProvince(@RequestBody ProvinceDTO dto) {
        return provinceService.saveUpdateProvince(dto);
    }

    /**
     * Get active province by id
     *
     * @param idProvince - province id
     * @return - Active province data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getById/{idProvince}")
    public ResponseEntity<CommonResponse> getActiveProvinceById(@PathVariable Long idProvince) {
        return provinceService.getActiveProvinceById(idProvince);
    }

    /**
     * Get all active provinces
     *
     * @return - All Active provinces data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActiveProvinces() {
        return provinceService.getAllActiveProvinces();
    }

    /**
     * Delete province by province id
     *
     * @param idProvince - province id
     * @return - Province deleted success info.
     * @author maleeshasa
     */
    @GetMapping(value = "/deleteById/{idProvince}")
    public ResponseEntity<CommonResponse> deleteProvinceById(@PathVariable Long idProvince) {
        return provinceService.deleteProvinceById(idProvince);
    }

    /**
     * Get provinces by country id
     *
     * @param idCountry - country id
     * @return - Provinces.
     * @author maleeshasa
     */
    @GetMapping(value = "/getByCountryId/{idCountry}")
    public ResponseEntity<CommonResponse> getProvincesByCountryId(@PathVariable Long idCountry) {
        return provinceService.getProvincesByCountryId(idCountry);
    }
}

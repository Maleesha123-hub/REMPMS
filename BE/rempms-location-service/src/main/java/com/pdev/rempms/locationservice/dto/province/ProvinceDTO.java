package com.pdev.rempms.locationservice.dto.province;

import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import lombok.Data;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Data
public class ProvinceDTO {
    private String idProvince;
    private String provinceName;
    private String commonStatus;
    private String idCountry;
    private CountryDTO country;
}

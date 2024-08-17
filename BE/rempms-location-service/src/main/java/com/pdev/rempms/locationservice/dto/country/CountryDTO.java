package com.pdev.rempms.locationservice.dto.country;

import lombok.Data;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Data
public class CountryDTO {
    private String idCountry;
    private String countryName;
    private String countryCode;
    private String nationality;
    private String commonStatus;
}

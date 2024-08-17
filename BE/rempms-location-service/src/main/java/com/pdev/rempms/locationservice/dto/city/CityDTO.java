package com.pdev.rempms.locationservice.dto.city;

import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import com.pdev.rempms.locationservice.dto.district.DistrictDTO;
import com.pdev.rempms.locationservice.dto.province.ProvinceDTO;
import lombok.Data;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Data
public class CityDTO {
    private String idCity;
    private String cityName;
    private String cityZipCode;
    private String commonStatus;
    private String idDistrict;
    private String idProvince;
    private String idCountry;
    private DistrictDTO district;
    private ProvinceDTO province;
    private CountryDTO country;
}

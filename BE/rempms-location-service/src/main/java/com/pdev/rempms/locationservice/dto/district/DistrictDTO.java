package com.pdev.rempms.locationservice.dto.district;

import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import com.pdev.rempms.locationservice.dto.province.ProvinceDTO;
import lombok.Data;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Data
public class DistrictDTO {
    private String idDistrict;
    private String districtName;
    private String commonStatus;
    private String idProvince;
    private String idCountry;
    private ProvinceDTO province;
    private CountryDTO country;
}

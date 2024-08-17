package com.pdev.rempms.candidateservice.dto.location.city;

import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.dto.location.district.DistrictDTO;
import com.pdev.rempms.candidateservice.dto.location.province.ProvinceDTO;
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

package com.pdev.rempms.candidateservice.dto.location.district;

import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.dto.location.province.ProvinceDTO;
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

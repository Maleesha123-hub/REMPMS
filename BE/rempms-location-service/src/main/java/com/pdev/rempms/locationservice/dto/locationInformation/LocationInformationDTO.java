package com.pdev.rempms.locationservice.dto.locationInformation;

import com.pdev.rempms.locationservice.dto.city.CityDTO;
import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import com.pdev.rempms.locationservice.dto.district.DistrictDTO;
import com.pdev.rempms.locationservice.dto.province.ProvinceDTO;
import com.pdev.rempms.locationservice.model.City;
import com.pdev.rempms.locationservice.model.Country;
import com.pdev.rempms.locationservice.model.District;
import com.pdev.rempms.locationservice.model.Province;
import lombok.Data;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Data
public class LocationInformationDTO {
    private String idLocationInformation;
    private String no;
    private String streetNo1;
    private String streetNo2;
    private String commonStatus;
    private String idCity;
    private String idDistrict;
    private String idProvince;
    private String idCountry;
    private CityDTO city;
    private DistrictDTO district;
    private ProvinceDTO province;
    private CountryDTO country;
}

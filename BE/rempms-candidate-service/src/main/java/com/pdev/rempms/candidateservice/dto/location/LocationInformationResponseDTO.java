package com.pdev.rempms.candidateservice.dto.location;

import com.pdev.rempms.candidateservice.dto.location.city.CityDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.dto.location.district.DistrictDTO;
import com.pdev.rempms.candidateservice.dto.location.province.ProvinceDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class LocationInformationResponseDTO { //location-service
    private String no;
    private String streetNo1;
    private String streetNo2;

    private Integer idDistrict;
    private DistrictDTO district;

    private Integer idProvince;
    private ProvinceDTO province;

    private Integer idCity;
    private CityDTO city;

    private Integer idCountry;
    private CountryDTO country;

    private Integer idLocationInformation;
    private LocationInformationResponseDTO locationInformation;

}

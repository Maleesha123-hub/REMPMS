package com.pdev.rempms.locationservice.mapper.location;

import com.pdev.rempms.locationservice.dto.city.CityDTO;
import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import com.pdev.rempms.locationservice.dto.district.DistrictDTO;
import com.pdev.rempms.locationservice.dto.locationInformation.LocationInformationDTO;
import com.pdev.rempms.locationservice.dto.province.ProvinceDTO;
import com.pdev.rempms.locationservice.mapper.city.CityMapper;
import com.pdev.rempms.locationservice.mapper.country.CountryMapper;
import com.pdev.rempms.locationservice.mapper.district.DistrictMapper;
import com.pdev.rempms.locationservice.mapper.province.ProvinceMapper;
import com.pdev.rempms.locationservice.model.*;
import com.pdev.rempms.locationservice.util.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author maleeshasa
 * @Date 22/11/2023
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class LocationInformationMapper {

    private final CityMapper cityMapper;
    private final DistrictMapper districtMapper;
    private final ProvinceMapper provinceMapper;
    private final CountryMapper countryMapper;

    public LocationInformationDTO toDto(LocationInformationDTO dto, LocationInformation locationInformation) {
        log.info("LocationInformationMapper -> toDto() => started!");
        CityDTO cityDTO = new CityDTO();
        DistrictDTO districtDTO = new DistrictDTO();
        CountryDTO countryDTO = new CountryDTO();
        ProvinceDTO provinceDTO = new ProvinceDTO();
        dto.setIdLocationInformation(locationInformation.getId().toString());
        dto.setNo(locationInformation.getNo());
        dto.setStreetNo1(locationInformation.getStreetNo1());
        dto.setStreetNo2(locationInformation.getStreetNo2());
        dto.setCommonStatus(locationInformation.getCommonStatus());
        dto.setCity(cityMapper.toDto(cityDTO, locationInformation.getCity()));
        dto.setDistrict(districtMapper.toDto(districtDTO, locationInformation.getDistrict()));
        dto.setProvince(provinceMapper.toDto(provinceDTO, locationInformation.getProvince()));
        dto.setCountry(countryMapper.toDto(countryDTO, locationInformation.getCountry()));
        log.info("LocationInformationMapper -> toDto() => ended!");
        return dto;
    }

    public LocationInformation toEntity(LocationInformation locationInformation, LocationInformationDTO dto,
                                        City city, District district, Province province, Country country) {
        log.info("LocationInformationMapper -> toEntity() => started!");
        if (!CommonValidation.stringNullValidation(dto.getIdLocationInformation())) {
            locationInformation.setId(Long.valueOf(dto.getIdLocationInformation()));
        }
        locationInformation.setNo(dto.getNo());
        locationInformation.setStreetNo1(dto.getStreetNo1());
        locationInformation.setStreetNo2(dto.getStreetNo2());
        locationInformation.setCommonStatus(dto.getCommonStatus());
        locationInformation.setCity(city);
        locationInformation.setProvince(province);
        locationInformation.setDistrict(district);
        locationInformation.setCountry(country);
        log.info("LocationInformationMapper -> toEntity() => ended!");
        return locationInformation;
    }
}

package com.pdev.rempms.locationservice.mapper.city;

import com.pdev.rempms.locationservice.dto.city.CityDTO;
import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import com.pdev.rempms.locationservice.dto.district.DistrictDTO;
import com.pdev.rempms.locationservice.dto.province.ProvinceDTO;
import com.pdev.rempms.locationservice.mapper.country.CountryMapper;
import com.pdev.rempms.locationservice.mapper.district.DistrictMapper;
import com.pdev.rempms.locationservice.mapper.province.ProvinceMapper;
import com.pdev.rempms.locationservice.model.City;
import com.pdev.rempms.locationservice.model.Country;
import com.pdev.rempms.locationservice.model.District;
import com.pdev.rempms.locationservice.model.Province;
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
public class CityMapper {

    private final CountryMapper countryMapper;
    private final ProvinceMapper provinceMapper;
    private final DistrictMapper districtMapper;

    public CityDTO toDto(CityDTO dto, City city) {
        log.info("CityMapper -> toDto() => started!");
        CountryDTO countryDTO = new CountryDTO();
        ProvinceDTO provinceDTO = new ProvinceDTO();
        DistrictDTO districtDTO = new DistrictDTO();
        dto.setIdCity(city.getId().toString());
        dto.setCityName(city.getCityName());
        dto.setCityZipCode(city.getCityZipCode().toString());
        dto.setCommonStatus(city.getCommonStatus());
        dto.setCountry(countryMapper.toDto(countryDTO, city.getCountry()));
        dto.setProvince(provinceMapper.toDto(provinceDTO, city.getProvince()));
        dto.setDistrict(districtMapper.toDto(districtDTO, city.getDistrict()));
        log.info("CityMapper -> toDto() => ended!");
        return dto;
    }

    public City toEntity(City city, CityDTO dto, Country country,
                         Province province, District district) {
        log.info("CityMapper -> toEntity() => started!");
        if (!CommonValidation.stringNullValidation(dto.getIdCity())) {
            city.setId(Long.valueOf(dto.getIdCity()));
        }
        city.setCityName(dto.getCityName());
        city.setCityZipCode(Integer.valueOf(dto.getCityZipCode()));
        city.setCommonStatus(dto.getCommonStatus());
        city.setCountry(country);
        city.setProvince(province);
        city.setDistrict(district);
        log.info("CityMapper -> toEntity() => ended!");
        return city;
    }
}

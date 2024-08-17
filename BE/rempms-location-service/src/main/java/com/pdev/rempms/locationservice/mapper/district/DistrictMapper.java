package com.pdev.rempms.locationservice.mapper.district;

import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import com.pdev.rempms.locationservice.dto.district.DistrictDTO;
import com.pdev.rempms.locationservice.dto.province.ProvinceDTO;
import com.pdev.rempms.locationservice.mapper.country.CountryMapper;
import com.pdev.rempms.locationservice.mapper.province.ProvinceMapper;
import com.pdev.rempms.locationservice.model.Country;
import com.pdev.rempms.locationservice.model.District;
import com.pdev.rempms.locationservice.model.Province;
import com.pdev.rempms.locationservice.util.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class DistrictMapper {

    private final ProvinceMapper provinceMapper;
    private final CountryMapper countryMapper;

    public DistrictDTO toDto(DistrictDTO dto, District district) {
        log.info("DistrictMapper -> toDto() => started!");
        ProvinceDTO provinceDTO = new ProvinceDTO();
        CountryDTO countryDTO = new CountryDTO();
        dto.setIdDistrict(district.getId().toString());
        dto.setDistrictName(district.getDistrictName());
        dto.setCommonStatus(district.getCommonStatus());
        dto.setProvince(provinceMapper.toDto(provinceDTO, district.getProvince()));
        dto.setCountry(countryMapper.toDto(countryDTO, district.getCountry()));
        log.info("DistrictMapper -> toDto() => ended!");
        return dto;
    }

    public District toEntity(District district, DistrictDTO dto, Province province, Country country) {
        log.info("DistrictMapper -> toEntity() => started!");
        if (!CommonValidation.stringNullValidation(dto.getIdDistrict())) {
            district.setId(Long.valueOf(dto.getIdDistrict()));
        }
        district.setDistrictName(dto.getDistrictName());
        district.setProvince(province);
        district.setCountry(country);
        district.setCommonStatus(dto.getCommonStatus());
        log.info("DistrictMapper -> toEntity() => ended!");
        return district;
    }
}

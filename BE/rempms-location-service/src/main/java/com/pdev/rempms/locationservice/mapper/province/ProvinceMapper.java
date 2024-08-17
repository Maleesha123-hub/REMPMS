package com.pdev.rempms.locationservice.mapper.province;

import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import com.pdev.rempms.locationservice.dto.province.ProvinceDTO;
import com.pdev.rempms.locationservice.mapper.country.CountryMapper;
import com.pdev.rempms.locationservice.model.Country;
import com.pdev.rempms.locationservice.model.Province;
import com.pdev.rempms.locationservice.util.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class ProvinceMapper {

    private final CountryMapper countryMapper;

    public ProvinceDTO toDto(ProvinceDTO dto, Province province) {
        log.info("ProvinceMapper -> toDto() => started");
        CountryDTO countryDTO = new CountryDTO();
        dto.setIdProvince(province.getId().toString());
        dto.setProvinceName(province.getProvinceName());
        dto.setCommonStatus(province.getCommonStatus());
        dto.setCountry(countryMapper.toDto(countryDTO, province.getCountry()));
        log.info("ProvinceMapper -> toDto() => ended");
        return dto;
    }

    public Province toEntity(Province province, ProvinceDTO dto, Country country) {
        log.info("ProvinceMapper -> toEntity() => started");
        if (!CommonValidation.stringNullValidation(dto.getIdProvince())) {
            province.setId(Long.valueOf(dto.getIdProvince()));
        }
        province.setProvinceName(dto.getProvinceName());
        province.setCountry(country);
        province.setCommonStatus(dto.getCommonStatus());
        log.info("ProvinceMapper -> toEntity() => ended");
        return province;
    }
}

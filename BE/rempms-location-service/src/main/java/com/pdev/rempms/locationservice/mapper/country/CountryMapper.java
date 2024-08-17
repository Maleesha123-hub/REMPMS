package com.pdev.rempms.locationservice.mapper.country;

import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import com.pdev.rempms.locationservice.model.Country;
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
public class CountryMapper {

    public CountryDTO toDto(CountryDTO dto, Country country) {
        log.info("CountryMapper -> toDto() => started!");
        dto.setIdCountry(country.getId().toString());
        dto.setCountryName(country.getCountryName());
        dto.setNationality(country.getNationality());
        dto.setCountryCode(country.getCountryCode().toString());
        dto.setCommonStatus(country.getCommonStatus());
        log.info("CountryMapper -> toDto() => ended!");
        return dto;
    }

    public Country toEntity(Country country, CountryDTO dto) {
        log.info("CountryMapper -> toEntity() => started!");
        if (!CommonValidation.stringNullValidation(dto.getIdCountry())) {
            country.setId(Long.valueOf(dto.getIdCountry()));
        }
        country.setCountryName(dto.getCountryName());
        country.setCountryCode(Integer.valueOf(dto.getCountryCode()));
        country.setNationality(dto.getNationality());
        country.setCommonStatus(dto.getCommonStatus());
        log.info("CountryMapper -> toEntity() => ended!");
        return country;
    }
}

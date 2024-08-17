package com.pdev.rempms.candidateservice.mapper.localtion;

import com.pdev.rempms.candidateservice.dto.candidate.personalDetail.PersonalDetailRequestDTO;
import com.pdev.rempms.candidateservice.dto.location.LocationInformationRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Component
@Slf4j
public class LocationInformationMapper {

    public LocationInformationRequestDTO toDto(LocationInformationRequestDTO dto,
                                               PersonalDetailRequestDTO personalDetail) {
        log.info("LocationInformationMapper -> toDto() => started!");
        if (!CommonValidation.integerNullValidation(personalDetail.getIdLocationInformation())){
            dto.setIdLocationInformation(personalDetail.getIdLocationInformation());
        }
        dto.setNo(personalDetail.getNo());
        dto.setStreetNo1(personalDetail.getStreetNo1());
        dto.setStreetNo2(personalDetail.getStreetNo2());
        dto.setIdDistrict(personalDetail.getIdDistrict());
        dto.setIdProvince(personalDetail.getIdProvince());
        dto.setIdCity(personalDetail.getIdCity());
        dto.setIdCountry(personalDetail.getIdCountry());
        log.info("LocationInformationMapper -> toDto() => ended!");
        return dto;
    }
}

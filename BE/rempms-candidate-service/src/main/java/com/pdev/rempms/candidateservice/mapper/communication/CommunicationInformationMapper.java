package com.pdev.rempms.candidateservice.mapper.communication;

import com.pdev.rempms.candidateservice.dto.candidate.personalDetail.PersonalDetailRequestDTO;
import com.pdev.rempms.candidateservice.dto.communication.CommunicationInformationRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommunicationInformationMapper {

    public CommunicationInformationRequestDTO toDto(CommunicationInformationRequestDTO dto,
                                                    PersonalDetailRequestDTO personalDetail) {
        log.info("CommunicationInformationMapper -> toDto() => started!");
        if (!CommonValidation.integerNullValidation(personalDetail.getIdCommunicationInformation())) {
            dto.setIdCommunicationInformation(personalDetail.getIdCommunicationInformation());
        }
        dto.setPhoneNo(personalDetail.getPhoneNo());
        dto.setMobileNo(personalDetail.getMobileNo());
        dto.setEmail(personalDetail.getEmail());
        dto.setCommInfoIdLanguage(personalDetail.getIdLanguage());
        dto.setCommInfoIdPreferredCommunication(personalDetail.getIdPreferredLanguage());
        log.info("CommunicationInformationMapper -> toDto() => ended!");
        return dto;
    }
}

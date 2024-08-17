package com.pdev.rempms.communicationservice.mapper;

import com.pdev.rempms.communicationservice.dto.communicationInformation.CommunicationInformationDTO;
import com.pdev.rempms.communicationservice.dto.language.LanguageDTO;
import com.pdev.rempms.communicationservice.dto.preferredCommunication.PreferredCommunicationDTO;
import com.pdev.rempms.communicationservice.model.CommunicationInformation;
import com.pdev.rempms.communicationservice.model.Language;
import com.pdev.rempms.communicationservice.model.PreferredCommunication;
import com.pdev.rempms.communicationservice.util.CommonValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Slf4j
@Component
public class CommunicationInformationMapper {

    @Autowired
    private LanguageMapper languageMapper;

    @Autowired
    private PreferredCommunicationMapper preferredCommunicationMapper;

    private static CommunicationInformationMapper instance = null;

    private CommunicationInformationMapper() {
    }

    public static CommunicationInformationMapper getInstance() {
        if (instance == null) {
            instance = new CommunicationInformationMapper();
        }
        return instance;
    }

    public CommunicationInformationDTO toDto(CommunicationInformationDTO dto, CommunicationInformation communicationInformation) {
        log.info("CommunicationInformationMapper -> toDto() => started!");
        LanguageDTO languageDTO = new LanguageDTO();
        PreferredCommunicationDTO preferredCommunicationDTO = new PreferredCommunicationDTO();
        dto.setIdCommunicationInformation(communicationInformation.getId().toString());
        dto.setEmail(communicationInformation.getEmail());
        dto.setPhoneNo(communicationInformation.getPhoneNo());
        dto.setMobileNo(communicationInformation.getMobileNo());
        dto.setCommonStatus(communicationInformation.getCommonStatus());
        dto.setLanguage(languageMapper.toDto(languageDTO, communicationInformation.getLanguage()));
        dto.setPreferredCommunication(preferredCommunicationMapper.toDto(preferredCommunicationDTO, communicationInformation.getPreferredCommunication()));
        log.info("CommunicationInformationMapper -> toDto() => ended!");
        return dto;
    }

    public CommunicationInformation toEntity(CommunicationInformation communicationInformation, CommunicationInformationDTO dto,
                                             Language language, PreferredCommunication preferredCommunication) {
        log.info("CommunicationInformationMapper -> toEntity() => started!");
        if (!CommonValidation.stringNullValidation(dto.getIdCommunicationInformation())) {
            communicationInformation.setId(Long.valueOf(dto.getIdCommunicationInformation()));
        }
        communicationInformation.setEmail(dto.getEmail());
        communicationInformation.setMobileNo(dto.getMobileNo());
        communicationInformation.setPhoneNo(dto.getPhoneNo());
        communicationInformation.setCommonStatus(dto.getCommonStatus());
        communicationInformation.setLanguage(language);
        communicationInformation.setPreferredCommunication(preferredCommunication);
        log.info("CommunicationInformationMapper -> toEntity() => ended!");
        return communicationInformation;
    }
}

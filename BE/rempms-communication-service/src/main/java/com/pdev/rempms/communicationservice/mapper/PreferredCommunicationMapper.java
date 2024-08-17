package com.pdev.rempms.communicationservice.mapper;

import com.pdev.rempms.communicationservice.dto.preferredCommunication.PreferredCommunicationDTO;
import com.pdev.rempms.communicationservice.model.PreferredCommunication;
import com.pdev.rempms.communicationservice.util.CommonValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Slf4j
@Component
public class PreferredCommunicationMapper {

    private static PreferredCommunicationMapper instance = null;

    private PreferredCommunicationMapper() {
    }

    public static PreferredCommunicationMapper getInstance() {
        if (instance == null) {
            instance = new PreferredCommunicationMapper();
        }
        return instance;
    }

    public PreferredCommunicationDTO toDto(PreferredCommunicationDTO dto, PreferredCommunication preferredCommunication) {
        log.info("PreferredCommunicationMapper -> toDto() => started!");
        dto.setIdPreferredCommunication(preferredCommunication.getId().toString());
        dto.setPreferredCommunicationMethod(preferredCommunication.getPreferredCommunicationMethod());
        dto.setPreferredCommunicationDescription(preferredCommunication.getPreferredCommunicationDescription());
        dto.setPreferredCommunicationCommonStatus(preferredCommunication.getCommonStatus());
        log.info("PreferredCommunicationMapper -> toDto() => ended!");
        return dto;
    }

    public PreferredCommunication toEntity(PreferredCommunication preferredCommunication, PreferredCommunicationDTO dto) {
        log.info("PreferredCommunicationMapper -> toEntity() => started!");
        if (!CommonValidation.stringNullValidation(dto.getIdPreferredCommunication())) {
            preferredCommunication.setId(Long.valueOf(dto.getIdPreferredCommunication()));
        }
        preferredCommunication.setPreferredCommunicationMethod(dto.getPreferredCommunicationMethod());
        preferredCommunication.setPreferredCommunicationDescription(dto.getPreferredCommunicationDescription());
        preferredCommunication.setCommonStatus(dto.getPreferredCommunicationCommonStatus());
        log.info("PreferredCommunicationMapper -> toEntity() => ended!");
        return preferredCommunication;
    }
}

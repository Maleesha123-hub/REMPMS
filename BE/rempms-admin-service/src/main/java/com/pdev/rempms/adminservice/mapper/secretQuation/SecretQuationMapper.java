package com.pdev.rempms.adminservice.mapper.secretQuation;

import com.pdev.rempms.adminservice.dto.secretQuation.SecretQuationDTO;
import com.pdev.rempms.adminservice.model.SecretQuation;
import com.pdev.rempms.adminservice.util.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author maleeshasa
 * @Date 29/12/2023
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SecretQuationMapper {

    public SecretQuationDTO toDto(SecretQuationDTO dto, SecretQuation secretQuation) {
        log.info("SecretQuationMapper -> toDto() => started!");
        dto.setIdSecretQuation(String.valueOf(secretQuation.getId()));
        dto.setQuation(secretQuation.getQuation());
        dto.setCommonStatus(secretQuation.getCommonStatus());
        log.info("SecretQuationMapper -> toDto() => ended!");
        return dto;
    }

    public SecretQuation toEntity(SecretQuation secretQuation, SecretQuationDTO dto) {
        log.info("SecretQuationMapper -> toEntity() => started!");
        if (!CommonValidation.stringNullValidation(dto.getIdSecretQuation())) {
            secretQuation.setId(Long.valueOf(dto.getIdSecretQuation()));
        }
        secretQuation.setQuation(dto.getQuation());
        secretQuation.setCommonStatus(dto.getCommonStatus());
        log.info("SecretQuationMapper -> toEntity() => ended!");
        return secretQuation;
    }

    public SecretQuationDTO toSyncDto(SecretQuationDTO dto, SecretQuation secretQuation) {
        log.info("SecretQuationMapper -> toSyncDto() => started!");
        dto.setIdSecretQuation(String.valueOf(secretQuation.getId()));
        dto.setQuation(secretQuation.getQuation());
        dto.setCommonStatus(secretQuation.getCommonStatus());
        dto.setAuditData(secretQuation.getAuditData());
        log.info("SecretQuationMapper -> toSyncDto() => ended!");
        return dto;
    }
}

package com.pdev.rempms.communicationservice.mapper;

import com.pdev.rempms.communicationservice.dto.language.LanguageDTO;
import com.pdev.rempms.communicationservice.model.Language;
import com.pdev.rempms.communicationservice.util.CommonValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Component
@Slf4j
public class LanguageMapper {

    private static LanguageMapper instance = null;

    private LanguageMapper() {
    }

    public static LanguageMapper getInstance() {
        if (instance == null) {
            instance = new LanguageMapper();
        }
        return instance;
    }

    public LanguageDTO toDto(LanguageDTO dto, Language language) {
        log.info("LanguageMapper -> toDto() => started!");
        dto.setIdLanguage(language.getId().toString());
        dto.setLanguageName(language.getLanguageName());
        dto.setLanguageDescription(language.getLanguageDescription());
        dto.setLanguageCommonStatus(language.getCommonStatus());
        log.info("LanguageMapper -> toDto() => ended!");
        return dto;
    }

    public Language toEntity(Language language, LanguageDTO dto) {
        log.info("LanguageMapper -> toEntity() => started!");
        if (!CommonValidation.stringNullValidation(dto.getIdLanguage())) {
            language.setId(Long.valueOf(dto.getIdLanguage()));
        }
        language.setLanguageName(dto.getLanguageName());
        language.setLanguageDescription(dto.getLanguageDescription());
        language.setCommonStatus(dto.getLanguageCommonStatus());
        log.info("LanguageMapper -> toEntity() => ended!");
        return language;
    }
}

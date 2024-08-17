package com.pdev.rempms.communicationservice.service.impl;

import com.pdev.rempms.communicationservice.constants.enums.CommonStatus;
import com.pdev.rempms.communicationservice.constants.validation.CommonValidationMessage;
import com.pdev.rempms.communicationservice.constants.validation.LanguageValidationMessage;
import com.pdev.rempms.communicationservice.dto.language.LanguageDTO;
import com.pdev.rempms.communicationservice.exception.RecordNotFoundException;
import com.pdev.rempms.communicationservice.mapper.LanguageMapper;
import com.pdev.rempms.communicationservice.model.AuditData;
import com.pdev.rempms.communicationservice.model.Language;
import com.pdev.rempms.communicationservice.repository.LanguageRepository;
import com.pdev.rempms.communicationservice.service.LanguageService;
import com.pdev.rempms.communicationservice.util.CommonResponse;
import com.pdev.rempms.communicationservice.util.CommonValidation;
import com.pdev.rempms.communicationservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    /**
     * save or update language
     *
     * @param dto - language saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> saveUpdateLanguage(LanguageDTO dto) {
        log.info("LanguageServiceImpl -> saveUpdateLanguage() => started!");
        CommonResponse commonResponse = new CommonResponse();
        Language language = new Language();
        String validation = validateLanguage(dto);
        if (validation != null) {
            log.info("LanguageServiceImpl -> saveUpdateLanguage() => Validation available!");
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(validation);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        if (!CommonValidation.stringNullValidation(dto.getIdLanguage())) {
            log.info("LanguageServiceImpl -> saveUpdateLanguage() => Update existing language!");
            language = languageRepository.findById(Long.valueOf(dto.getIdLanguage())).get();
            language = languageMapper.toEntity(language, dto);
            language.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            language.getAuditData().setUpdatedBy(Long.valueOf(1)); //need further development for authorization
        } else {
            log.info("LanguageServiceImpl -> saveUpdateLanguage() => Save new language!");
            AuditData auditData = new AuditData();
            auditData.setCreatedOn(DateTimeUtil.getSriLankaTime());
            auditData.setCreatedBy(Long.valueOf(1)); //need further development for authorization
            language.setAuditData(auditData);
            language = languageMapper.toEntity(language, dto);
        }
        languageRepository.save(language);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(LanguageValidationMessage.LANGUAGE_SAVED_SUCCESS);
        log.info("LanguageServiceImpl -> saveUpdateLanguage() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Validate language save data
     *
     * @param dto - language saving or updating details
     * @return - Validation messages
     * @author maleeshasa
     */
    private String validateLanguage(LanguageDTO dto) {
        log.info("LanguageServiceImpl -> validateLanguage() => started!");
        String validation = null;
        if (CommonValidation.stringNullValidation(dto.getLanguageName())) {
            validation = CommonValidationMessage.NAME_EMPTY;
        } else if (CommonValidation.stringNullValidation(dto.getLanguageDescription())) {
            validation = CommonValidationMessage.DESCRIPTION_EMPTY;
        } else if (dto.getLanguageCommonStatus().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COMMON_STATUS_EMPTY;
        }
        log.info("LanguageServiceImpl -> validateLanguage() => ended!");
        return validation;
    }

    /**
     * Get active language by id
     *
     * @param id - language id
     * @return - Active language data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getActiveLanguageById(Long id) {
        log.info("LanguageServiceImpl -> getActiveLanguageById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        Language language = languageRepository.getLanguageByIdAndCommonStatus(id, CommonStatus.ACTIVE.getValue());
        if (language != null) {
            log.info("LanguageServiceImpl -> getActiveLanguageById() => Language found");
            LanguageDTO dto = new LanguageDTO();
            dto = languageMapper.toDto(dto, language);
            commonResponse.setMessage(LanguageValidationMessage.LANGUAGE_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("LanguageServiceImpl -> getActiveLanguageById() => Language not found");
            throw new RecordNotFoundException(LanguageValidationMessage.LANGUAGE_NOT_FOUND);
        }
        log.info("LanguageServiceImpl -> getActiveLanguageById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all active languages
     *
     * @return - All Active languages data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActiveLanguages() {
        log.info("LanguageServiceImpl -> getAllActiveLanguages() => started!");
        CommonResponse commonResponse = new CommonResponse();
        List<Language> languages = languageRepository.findAll();
        if (!languages.isEmpty()) {
            log.info("LanguageServiceImpl -> getAllActiveLanguages() => Languages found!");
            List<LanguageDTO> languageDTOList = languages.stream()
                    .filter(language -> language.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                    .map(language -> {
                        LanguageDTO dto = new LanguageDTO();
                        return languageMapper.toDto(dto, language);
                    })
                    .toList();
            commonResponse.setMessage(LanguageValidationMessage.LANGUAGES_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(languageDTOList);
        } else {
            log.info("LanguageServiceImpl -> getAllActiveLanguages() => Languages not found!");
            throw new RecordNotFoundException(LanguageValidationMessage.LANGUAGE_NOT_FOUND);
        }
        log.info("LanguageServiceImpl -> getAllActiveLanguages() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete language by language id
     *
     * @param id - language id
     * @return - Language deleted success info.
     * @author maleeshasa
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deleteLanguageById(Long id) {
        log.info("LanguageServiceImpl -> deleteLanguageById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        Optional<Language> language = languageRepository.findById(id);
        if (language.isPresent()) {
            log.info("LanguageServiceImpl -> deleteLanguageById() => Language found!");
            language.get().setCommonStatus(CommonStatus.DELETED.getValue());
            language.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            language.get().getAuditData().setUpdatedBy(Long.valueOf(1));
            languageRepository.save(language.get());
            commonResponse.setMessage(LanguageValidationMessage.LANGUAGE_DELETED_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("LanguageServiceImpl -> deleteLanguageById() => Language not found!");
            throw new RecordNotFoundException(LanguageValidationMessage.LANGUAGE_NOT_FOUND);
        }
        log.info("LanguageServiceImpl -> deleteLanguageById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

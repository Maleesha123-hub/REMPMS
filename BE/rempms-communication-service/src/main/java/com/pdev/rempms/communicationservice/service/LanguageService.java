package com.pdev.rempms.communicationservice.service;

import com.pdev.rempms.communicationservice.dto.language.LanguageDTO;
import com.pdev.rempms.communicationservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
public interface LanguageService {

    /**
     * save or update language
     *
     * @param dto - language saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdateLanguage(LanguageDTO dto);

    /**
     * Get active language by id
     *
     * @param id - language id
     * @return - Active language data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActiveLanguageById(Long id);

    /**
     * Get all active languages
     *
     * @return - All Active languages data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActiveLanguages();

    /**
     * Delete language by language id
     *
     * @param id - language id
     * @return - Language deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deleteLanguageById(Long id);
}

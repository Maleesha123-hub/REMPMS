package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/09
 */
public interface LanguageProficiencyService {

    /**
     * This method is allowed to save or update language proficiency details
     *
     * @param dto {@link CommonProfileRequestDTO} - language proficiency saved response
     * @return {@link CommonResponse} - language proficiency saved response
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);

}

package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
public interface JobPreferenceService {

    /**
     * This method is allowed to save or update job preferences
     *
     * @param dto {@link CommonProfileRequestDTO} - including job preferences details
     * @return {@link CommonResponse} - job preferences saved response
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);

}

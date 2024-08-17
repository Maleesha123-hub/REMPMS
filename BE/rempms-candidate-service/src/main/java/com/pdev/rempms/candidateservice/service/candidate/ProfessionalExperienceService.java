package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/04
 */
public interface ProfessionalExperienceService {

    /**
     * This method is allowed to save update professional experiences of candidate
     *
     * @param dto {@link CommonProfileRequestDTO} - including professional experiences details
     * @return - {@link CommonResponse} - save success info.
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);
}

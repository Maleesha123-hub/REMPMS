package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/09
 */
public interface ResearchService {

    /**
     * This method is allowed to save or update researches
     *
     * @param dto {@link CommonProfileRequestDTO} - researches saved response
     * @return {@link CommonResponse} - researches saved response
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);
}

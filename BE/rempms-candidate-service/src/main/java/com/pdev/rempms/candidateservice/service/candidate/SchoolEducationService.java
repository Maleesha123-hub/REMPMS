package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/05
 */
public interface SchoolEducationService {

    /**
     * This method is allowed to save school education details
     *
     * @param dto {@link CommonProfileRequestDTO} - including school education details
     * @return {@link CommonResponse} - school education saved response
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);
}

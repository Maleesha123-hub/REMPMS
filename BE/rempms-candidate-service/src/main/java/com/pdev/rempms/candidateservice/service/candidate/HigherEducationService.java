package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/04
 */
public interface HigherEducationService {

    /**
     * This method is allowed to save higher education details
     *
     * @param dto {@link CommonProfileRequestDTO} - including higher education details
     * @return {@link CommonResponse} - higher education saved response
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);

}

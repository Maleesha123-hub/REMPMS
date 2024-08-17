package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
public interface PreferredJobLocationService {

    /**
     * This method is allowed to save or update preferred job locations
     *
     * @param dto {@link CommonProfileRequestDTO} - including preferred job locations details
     * @return {@link CommonResponse} - preferred job locations saved response
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);

}

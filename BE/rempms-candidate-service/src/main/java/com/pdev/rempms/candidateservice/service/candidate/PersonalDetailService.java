package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
public interface PersonalDetailService {

    /**
     * This method is allowed to save update personal details of candidate
     *
     * @param dto {@link CommonProfileRequestDTO} - including personal details
     * @return - {@link CommonResponse} - save success info.
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);

}

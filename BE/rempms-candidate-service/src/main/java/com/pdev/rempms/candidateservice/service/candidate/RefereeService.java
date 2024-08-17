package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
public interface RefereeService {

    /**
     * This method is allowed to save or update referees
     *
     * @param dto {@link CommonProfileRequestDTO} - including referees details
     * @return {@link CommonResponse} - referees saved response
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);

}

package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
public interface MembershipService {

    /**
     * This method is allowed to save membership details
     *
     * @param dto {@link CommonProfileRequestDTO} - including membership details
     * @return {@link CommonResponse} - membership saved response
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);
}

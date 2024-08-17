package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
public interface FamilyInformationService {

    /**
     * This method is allowed to save or update family information
     *
     * @param dto {@link CommonProfileRequestDTO} - including family information details
     * @return {@link CommonResponse} - family information saved response
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);

}

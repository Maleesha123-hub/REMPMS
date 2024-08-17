package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/09
 */
public interface AchievementService {

    /**
     * This method is allowed to save or update achievements
     *
     * @param dto {@link CommonProfileRequestDTO} - including achievements details
     * @return {@link CommonResponse} - achievements saved response
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);

}

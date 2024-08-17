package com.pdev.rempms.candidateservice.service;

import com.pdev.rempms.candidateservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author @maleeshasa
 * @Date 2024/03/07
 */
public interface AreaOfStudyService {

    /**
     * This method is allowed to get active area of studies
     *
     * @return {@link CommonResponse}
     * @author @maleeshasa
     */
    CommonResponse getActiveAreaOfStudies();

}

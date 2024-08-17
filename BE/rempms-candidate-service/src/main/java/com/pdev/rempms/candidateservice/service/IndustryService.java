package com.pdev.rempms.candidateservice.service;

import com.pdev.rempms.candidateservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author @maleeshasa
 * @Date 2024/03/07
 */
public interface IndustryService {

    /**
     * This method is allowed to get active industries
     *
     * @return {@link ResponseEntity <CommonResponse>}
     * @author @maleeshasa
     */
    CommonResponse getActiveIndustries();

    CommonResponse getById(Integer id);
}

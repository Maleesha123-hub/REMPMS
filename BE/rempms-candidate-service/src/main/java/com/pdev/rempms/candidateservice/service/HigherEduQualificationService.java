package com.pdev.rempms.candidateservice.service;

import com.pdev.rempms.candidateservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author @Maleesha99
 * @Date 2024/03/12
 */
public interface HigherEduQualificationService {

    /**
     * This method is allowed to get active higher educations
     *
     * @return {@link CommonResponse}
     * @author @maleeshasa
     */
    CommonResponse getAllActiveHigherEduQualification();

    /**
     * This method is allowed to get higher education qualification by id
     *
     * @return {@link ResponseEntity <CommonResponse>}
     * @author @maleeshasa
     */
    CommonResponse getById(Integer id);

}

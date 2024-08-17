package com.pdev.rempms.candidateservice.service;

import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
public interface JobCategoryService {

    /**
     * Get all active job categories
     *
     * @return - All Active job categories data.
     * @author maleeshasa
     */
    CommonResponse getAllActive();

}

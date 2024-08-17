package com.pdev.rempms.adminservice.service.jobCategory;

import com.pdev.rempms.adminservice.dto.jobCategory.JobCategoryDTO;
import com.pdev.rempms.adminservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
public interface JobCategoryService {

    /**
     * save or update job category
     *
     * @param dto - job category saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdate(@RequestBody JobCategoryDTO dto);

    /**
     * Get all active job categories
     *
     * @return - All Active job categories data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActive();

    /**
     * Get active job category by id
     *
     * @param idJobCategory - job category id
     * @return - Active job category data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActiveById(@PathVariable Long idJobCategory);

    /**
     * Delete job category by user account id
     *
     * @param idJobCategory - job category id
     * @return - Job category deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deleteById(@PathVariable Long idJobCategory);
}

package com.pdev.rempms.adminservice.controller;

import com.pdev.rempms.adminservice.dto.jobCategory.JobCategoryDTO;
import com.pdev.rempms.adminservice.service.jobCategory.JobCategoryService;
import com.pdev.rempms.adminservice.util.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/job-category")
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    /**
     * save or update job category
     *
     * @param dto - job category saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdate(@Valid @RequestBody JobCategoryDTO dto) {
        return jobCategoryService.saveUpdate(dto);
    }

    /**
     * Get active job category by id
     *
     * @param idJobCategory - job category id
     * @return - Active job category data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getById/{idJobCategory}")
    public ResponseEntity<CommonResponse> getActiveById(@PathVariable Long idJobCategory) {
        return jobCategoryService.getActiveById(idJobCategory);
    }

    /**
     * Get all active job categories
     *
     * @return - All Active job categories data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActive() {
        return jobCategoryService.getAllActive();
    }

    /**
     * Delete job category by user account id
     *
     * @param idJobCategory - job category id
     * @return - Job category deleted success info.
     * @author maleeshasa
     */
    @GetMapping(value = "/deleteById/{idJobCategory}")
    public ResponseEntity<CommonResponse> deleteById(@PathVariable Long idJobCategory) {
        return jobCategoryService.deleteById(idJobCategory);
    }
}

package com.pdev.rempms.candidateservice.controller;

import com.pdev.rempms.candidateservice.service.JobCategoryService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author @maleeshasa
 * @Date 2024/03/07
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/candidate/v1/job-category")
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    /**
     * Get all active job categories
     *
     * @return - All Active job categories data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActive() {
        log.info("JobCategoryController.getAllActive() => started.");

        return ResponseEntity.ok(jobCategoryService.getAllActive());

    }

}

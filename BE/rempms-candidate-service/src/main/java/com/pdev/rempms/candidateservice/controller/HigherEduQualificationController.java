package com.pdev.rempms.candidateservice.controller;

import com.pdev.rempms.candidateservice.service.HigherEduQualificationService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author @maleeshasa
 * @Date 2024/03/07
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/candidate/v1/higher-edu-qualification")
public class HigherEduQualificationController {

    private final HigherEduQualificationService higherEduQualificationService;

    /**
     * This method is allowed to get active higher educations
     *
     * @return {@link ResponseEntity<CommonResponse>}
     * @author @maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActiveHigherEduQualification() {
        log.info("HigherEduQualificationController.getAllActiveHigherEduQualification() => started.");

        return ResponseEntity.ok(higherEduQualificationService.getAllActiveHigherEduQualification());

    }

    /**
     * This method is allowed to get higher education qualification by id
     *
     * @return {@link ResponseEntity<CommonResponse>}
     * @author @maleeshasa
     */
    @GetMapping(value = "/getById")
    public ResponseEntity<CommonResponse> getById(@RequestParam(value = "id") Integer id) {
        log.info("HigherEduQualificationController.getById() => started.");

        return ResponseEntity.ok(higherEduQualificationService.getById(id));

    }

}

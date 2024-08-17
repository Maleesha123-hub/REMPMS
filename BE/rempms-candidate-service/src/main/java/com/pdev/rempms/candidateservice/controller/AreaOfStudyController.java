package com.pdev.rempms.candidateservice.controller;

import com.pdev.rempms.candidateservice.service.AreaOfStudyService;
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
@RequestMapping(value = "/api/candidate/v1/area-of-study")
public class AreaOfStudyController {

    private final AreaOfStudyService areaOfStudyService;

    /**
     * This method is allowed to get active area of studies
     *
     * @return {@link ResponseEntity<CommonResponse>}
     * @author @maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getActiveAreaOfStudies() {
        log.info("AreaOfStudyController.getActiveAreaOfStudies() => started.");

        return ResponseEntity.ok(areaOfStudyService.getActiveAreaOfStudies());

    }

}

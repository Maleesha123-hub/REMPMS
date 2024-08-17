package com.pdev.rempms.candidateservice.controller;

import com.pdev.rempms.candidateservice.service.ResearchAreaService;
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
@RequestMapping(value = "/api/candidate/v1/research-area")
public class ResearchAreaController {

    private final ResearchAreaService researchAreaService;

    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getActiveResearchAreas() {

        return ResponseEntity.ok(researchAreaService.getActiveResearchAreas());

    }

}

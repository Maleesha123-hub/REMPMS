package com.pdev.rempms.candidateservice.controller;

import com.pdev.rempms.candidateservice.service.IndustryService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author @maleeshasa
 * @Date 2024/03/07
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/candidate/v1/industry")
public class IndustryController {

    private final IndustryService industryService;

    /**
     * This method is allowed to get active industries
     *
     * @return {@link ResponseEntity<CommonResponse>}
     * @author @maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getActiveIndustries() {
        log.info("IndustryController.getActiveIndustries() => started.");

        return ResponseEntity.ok(industryService.getActiveIndustries());

    }

    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(industryService.getById(id));
    }

}

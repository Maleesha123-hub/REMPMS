package com.pdev.rempms.candidateservice.controller;

import com.pdev.rempms.candidateservice.dto.candidate.CandidateDTO;
import com.pdev.rempms.candidateservice.service.candidate.CandidateService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/candidate/v1")
public class CandidateController {

    private final CandidateService candidateService;

    /**
     * save candidate
     *
     * @return - {@link ResponseEntity<CommonResponse>} - save success info.
     * @author @Maleesha99
     */
    @PostMapping(value = "/save")
    public ResponseEntity<CommonResponse> saveCandidate(@RequestBody CandidateDTO dto) {
        log.info("CandidateController.saveCandidate() => started.");

        CommonResponse response = candidateService.saveCandidate(dto);

        log.info("CandidateController.saveCandidate() => ended.");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * This method is allowed to get candidate details by id
     *
     * @param idCandidate {@link Integer} - candidate id
     * @return - {@link ResponseEntity<CommonResponse>} - candidate response.
     * @author @Maleesha99
     */
    @GetMapping(value = "/getById")
    public ResponseEntity<CommonResponse> getById(@RequestParam("id") Integer idCandidate) {
        log.info("CandidateController.getById() => started.");

        CommonResponse response = candidateService.getById(idCandidate);

        log.info("CandidateController.getById() => ended.");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}

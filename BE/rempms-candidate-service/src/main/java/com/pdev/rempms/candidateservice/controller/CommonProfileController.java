package com.pdev.rempms.candidateservice.controller;

import com.pdev.rempms.candidateservice.dto.candidate.CandidateSearchParamsDTO;
import com.pdev.rempms.candidateservice.service.CommonProfileService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/api/candidate/v1/common-profile")
public class CommonProfileController {

    private final CommonProfileService commonProfileService;

    /**
     * save candidate common profile
     *
     * @param idCandidate - candidate id
     * @return - {@link ResponseEntity<CommonResponse>} - save success info.
     * @author @Maleesha99
     */
    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdate(@RequestParam(value = "idCandidate") Integer idCandidate) {
        log.info("CommonProfileController.saveUpdate() => started.");

        CommonResponse response = commonProfileService.saveUpdateByJPA(idCandidate);

        log.info("CommonProfileController.saveUpdate() => ended.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
//    @PostMapping(value = "/saveUpdate")
//    public ResponseEntity<CommonResponse> saveUpdate(@RequestPart("commonProfileDetails") CommonProfileRequestDTO dto,
//                                                     @RequestPart(name = "documentDetails", required = false) List<DocumentDetails> documentDetails,
//                                                     @RequestPart(name = "documents", required = false) MultipartFile[] documents) {
//        log.info("CommonProfileController.saveUpdate() => started.");
//
//        CommonResponse response = commonProfileService.saveUpdateByJPA(dto);
//
//        log.info("CommonProfileController.saveUpdate() => ended.");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//
//    }

    /**
     * This method is allowed to get candidate profile by candidate id
     *
     * @param candidateId {@link Integer candidateId} - candidate id
     * @return - {@link ResponseEntity<CommonResponse>} - fetched response.
     * @author @Maleesha99
     */
    @GetMapping(value = "/getById/{candidateId}")
    public ResponseEntity<CommonResponse> getByCandidateId(@PathVariable(value = "candidateId") Integer candidateId) {
        log.info("CommonProfileController.getByCandidateId() => started.");

        CommonResponse response = commonProfileService.getByCandidateId(candidateId);

        log.info("CommonProfileController.getByCandidateId() => ended.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This method is allowed to search candidates
     *
     * @param limit                 {@link Integer} - limit of elements for page request object
     * @param page                  {@link Integer} - page for page request object
     * @param candidateSearchParams {@link CandidateSearchParamsDTO} - candidate search parameters
     * @return {@link ResponseEntity<CommonResponse>} - searched candidates
     * @author @Maleesha99
     */
    @PostMapping(value = "/searchCandidates")
    public ResponseEntity<CommonResponse> searchCandidates(@RequestParam(value = "limit") Integer limit,
                                                           @RequestParam(value = "page") Integer page,
                                                           @RequestBody CandidateSearchParamsDTO candidateSearchParams) {
        log.info("CommonProfileController.searchCandidates() => started.");

        CommonResponse commonResponse = commonProfileService.searchCandidates(candidateSearchParams, PageRequest.of(page, limit));

        log.info("CommonProfileController.searchCandidates() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

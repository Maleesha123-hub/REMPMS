package com.pdev.rempms.draftservice.controller.v2;

import com.pdev.rempms.draftservice.dto.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.draftservice.model.CandidateCommonProfileDraft;
import com.pdev.rempms.draftservice.service.v2.CandidateCommonProfileDraftServiceV2;
import com.pdev.rempms.draftservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author @Maleesha99
 * @Date 2024/03/01
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/draft/v2/candidate-common-profile")
public class CandidateCommonProfileDraftControllerV2 {

    private final CandidateCommonProfileDraftServiceV2 candidateCommonProfileDraftServiceV2;

    /**
     * This method is allowed to save or modify candidate common profile
     *
     * @param commonProfileRequest {@link CandidateCommonProfileDraft}
     * @return {@link ResponseEntity<CommonResponse>} - draft saved response
     * @author @Maleesha99
     */
    @PostMapping(value = "/createOrModify")
    public ResponseEntity<CommonResponse> createOrModify(@RequestPart(value = "commonProfileDetails") CommonProfileRequestDTO commonProfileRequest,
                                                         @RequestPart(value = "documents", required = false) MultipartFile[] documentList) {
        log.info("CandidateCommonProfileDraftController.createOrModify() => started.");

        return ResponseEntity.ok(candidateCommonProfileDraftServiceV2.createOrModify(commonProfileRequest, documentList));

    }

    /**
     * find by candidate id
     *
     * @param idCandidate
     * @return {@link ResponseEntity<CommonResponse>} - draft response by candidate id
     * @author @Maleesha99
     */
    @GetMapping(value = "/findByIdCandidate")
    public ResponseEntity<CommonResponse> findByIdCandidate(@RequestParam("idCandidate") Integer idCandidate) {
        log.info("CandidateCommonProfileDraftController.findByIdCandidate() => started.");

        return ResponseEntity.ok(candidateCommonProfileDraftServiceV2.findByIdCandidate(idCandidate));

    }

}

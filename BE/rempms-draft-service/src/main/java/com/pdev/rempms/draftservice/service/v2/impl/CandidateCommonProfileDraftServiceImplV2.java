package com.pdev.rempms.draftservice.service.v2.impl;

import com.pdev.rempms.draftservice.builder.SequenceBuilder;
import com.pdev.rempms.draftservice.dto.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.draftservice.dto.commonProfile.CommonProfileResponseDTO;
import com.pdev.rempms.draftservice.exception.BaseException;
import com.pdev.rempms.draftservice.exception.RecordNotFoundException;
import com.pdev.rempms.draftservice.mapper.CandidateCommonProfileDraft.CandidateCommonProfileDraftMapper;
import com.pdev.rempms.draftservice.model.CandidateCommonProfileDraft;
import com.pdev.rempms.draftservice.repository.CandidateCommonProfileDraftRepository;
import com.pdev.rempms.draftservice.service.v2.CandidateCommonProfileDraftServiceV2;
import com.pdev.rempms.draftservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Year;

/**
 * @author @Maleesha99
 * @Date 2024/03/01
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CandidateCommonProfileDraftServiceImplV2 implements CandidateCommonProfileDraftServiceV2 {

    private final CandidateCommonProfileDraftRepository candidateCommonProfileDraftRepository;
    private final SequenceBuilder sequenceBuilder;
    private final CandidateCommonProfileDraftMapper candidateCommonProfileDraftMapper;

    /**
     * find by candidate id
     *
     * @param idCandidate
     * @return {@link CommonResponse} - draft by candidate id
     * @author @Maleesha99
     */
    @Override
    public CommonResponse findByIdCandidate(Integer idCandidate) {
        log.info("CandidateCommonProfileDraftServiceImpl.findByIdCandidate() => started.");

        CandidateCommonProfileDraft profileDraft = candidateCommonProfileDraftRepository.findByIdCandidate(idCandidate)
                .orElseThrow(() -> new RecordNotFoundException("Candidate profile draft is not exists."));

        CommonResponse response = new CommonResponse();
        response.setData(profileDraft);
        response.setMessage("Candidate profile draft is exists.");
        response.setStatus(HttpStatus.OK);

        log.info("CandidateCommonProfileDraftServiceImpl.findByIdCandidate() => ended.");
        return response;

    }

    /**
     * This method is allowed to save or modify candidate common profile
     *
     * @param commonProfileRequest {@link CommonProfileRequestDTO}
     * @param documentList         {@link  {@link MultipartFile[]}}
     * @return {@link CommonResponse} - draft saved response
     * @author @Maleesha99
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse createOrModify(CommonProfileRequestDTO commonProfileRequest, MultipartFile[] documentList) {
        log.info("CandidateCommonProfileDraftServiceImpl.createOrModify() => started");

        CandidateCommonProfileDraft candidateCommonProfileDraft;

        if (commonProfileRequest.getId() == null) {
            log.info("New draft...");

            candidateCommonProfileDraft = new CandidateCommonProfileDraft();

            // Generate draft id
            candidateCommonProfileDraft.setId(sequenceBuilder.generateSequence(CandidateCommonProfileDraft.SEQUENCE_NAME));

            // Generate draft no
            candidateCommonProfileDraft.setDraftNo(sequenceBuilder.generateNextReference("DRF", String.valueOf(Year.now().getValue())));

        } else {
            log.info("Existing draft...");

            candidateCommonProfileDraft = candidateCommonProfileDraftRepository.findById(commonProfileRequest.getId())
                    .orElseThrow(() -> new RecordNotFoundException("Candidate common profile draft not found by id."));

        }

        log.info("Mapping candidate common profile dto to entity...");
        // Candidate common profile dto to entity mapping
        candidateCommonProfileDraftMapper.toEntityV2(candidateCommonProfileDraft, commonProfileRequest, documentList);
        log.info("Mapped candidate common profile dto to entity...");

        try {
            log.info("Candidate common profile saving...");
            // Save candidate common profile entity
            CandidateCommonProfileDraft savedObj = candidateCommonProfileDraftRepository.save(candidateCommonProfileDraft);
            log.info("Candidate common profile saved.");

            // Generate common response
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage(commonProfileRequest.getId() == null ? "Draft saved" : "Draft modified.");
            commonResponse.setData(candidateCommonProfileDraftMapper.toDto(new CommonProfileResponseDTO(), savedObj));
            return commonResponse;

        } catch (Exception e) {
            throw new BaseException(500, "Exception occurred while saving candidate draft. " + e.getMessage());
        }

    }

}

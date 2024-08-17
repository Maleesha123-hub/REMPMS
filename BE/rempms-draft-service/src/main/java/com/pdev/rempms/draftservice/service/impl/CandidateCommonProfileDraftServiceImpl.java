package com.pdev.rempms.draftservice.service.impl;

import com.pdev.rempms.draftservice.builder.SequenceBuilder;
import com.pdev.rempms.draftservice.dto.commonProfile.CommonProfileDraftLazyResponseDTO;
import com.pdev.rempms.draftservice.dto.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.draftservice.exception.RecordNotFoundException;
import com.pdev.rempms.draftservice.mapper.CandidateCommonProfileDraft.CandidateCommonProfileDraftMapper;
import com.pdev.rempms.draftservice.model.CandidateCommonProfileDraft;
import com.pdev.rempms.draftservice.repository.CandidateCommonProfileDraftRepository;
import com.pdev.rempms.draftservice.service.CandidateCommonProfileDraftService;
import com.pdev.rempms.draftservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Year;

/**
 * @author @Maleesha99
 * @Date 2024/03/01
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CandidateCommonProfileDraftServiceImpl implements CandidateCommonProfileDraftService {

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
        response.setMessage("Candidate profile draft is not exists.");
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
        candidateCommonProfileDraftMapper.toEntity(candidateCommonProfileDraft, commonProfileRequest, documentList);
        log.info("Mapped candidate common profile dto to entity...");

        log.info("Candidate common profile saving...");
        // Save candidate common profile entity
        candidateCommonProfileDraftRepository.save(candidateCommonProfileDraft);
        log.info("Candidate common profile saved.");

        // Generate common response
        CommonResponse response = new CommonResponse();
        response.setStatus(HttpStatus.OK);
        response.setMessage(commonProfileRequest.getId() == null ? "Draft saved" : "Draft modified.");
        response.setData(new CommonProfileDraftLazyResponseDTO(candidateCommonProfileDraft.getId(),
                candidateCommonProfileDraft.getCreatedDate(), candidateCommonProfileDraft.getCreatedBy()));

        log.info("CandidateCommonProfileDraftServiceImpl.createOrModify() => ended");

        return response;

    }

}

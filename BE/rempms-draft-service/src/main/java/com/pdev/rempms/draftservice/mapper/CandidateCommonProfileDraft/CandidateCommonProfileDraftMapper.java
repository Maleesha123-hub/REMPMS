package com.pdev.rempms.draftservice.mapper.CandidateCommonProfileDraft;

import com.pdev.rempms.draftservice.dto.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.draftservice.dto.document.CommonProfileDocumentRequest;
import com.pdev.rempms.draftservice.exception.BaseException;
import com.pdev.rempms.draftservice.model.CandidateCommonProfileDraft;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author @Maleesha99
 * @Date 2024/03/02
 */
@Getter
@Setter
@Slf4j
@Component
public class CandidateCommonProfileDraftMapper {

    /**
     * This method is allowed to get entity by dto
     *
     * @param candidateCommonProfileDraft {@link CandidateCommonProfileDraft}
     * @param commonProfileRequestDTO     {@link CommonProfileRequestDTO}
     * @param documentList                {@link MultipartFile[]}
     * @return {@link CandidateCommonProfileDraft}
     * @author @Maleesha99
     */
    public CandidateCommonProfileDraft toEntity(CandidateCommonProfileDraft candidateCommonProfileDraft,
                                                CommonProfileRequestDTO commonProfileRequestDTO, MultipartFile[] documentList) {
        log.info("CandidateCommonProfileDraftMapper.toEntity() => started.");

        candidateCommonProfileDraft.setIdCandidate(commonProfileRequestDTO.getIdCandidate());
        candidateCommonProfileDraft.setCreatedBy("Maleesha99");
        candidateCommonProfileDraft.setCreatedDate(LocalDateTime.now());

        if (commonProfileRequestDTO.getPersonalDetail() != null) {
            log.info("Personal details are exists.");
            candidateCommonProfileDraft.setPersonalDetail(commonProfileRequestDTO.getPersonalDetail());
        }

        if (commonProfileRequestDTO.getProfessionalExperiences() == null || !commonProfileRequestDTO.getProfessionalExperiences().isEmpty()) {
            log.info("Professional experiences are exists.");
            candidateCommonProfileDraft.setProfessionalExperiences(commonProfileRequestDTO.getProfessionalExperiences());
        }

        if (commonProfileRequestDTO.getAchievements() != null) {
            log.info("Achievements are exists.");
            candidateCommonProfileDraft.setAchievements(commonProfileRequestDTO.getAchievements());
        }

        if (commonProfileRequestDTO.getMemberships() == null || !commonProfileRequestDTO.getMemberships().isEmpty()) {
            log.info("Memberships are exists.");
            candidateCommonProfileDraft.setMemberships(commonProfileRequestDTO.getMemberships());
        }

        if (commonProfileRequestDTO.getHigherEducations() == null || !commonProfileRequestDTO.getHigherEducations().isEmpty()) {
            log.info("Higher educations are exists.");
            candidateCommonProfileDraft.setHigherEducations(commonProfileRequestDTO.getHigherEducations());
        }

        if (commonProfileRequestDTO.getFamilyInformation() == null || !commonProfileRequestDTO.getFamilyInformation().isEmpty()) {
            log.info("Family information are exists.");
            candidateCommonProfileDraft.setFamilyInformation(commonProfileRequestDTO.getFamilyInformation());
        }

        if (commonProfileRequestDTO.getReferees() == null || !commonProfileRequestDTO.getReferees().isEmpty()) {
            log.info("Referees are exists.");
            candidateCommonProfileDraft.setReferees(commonProfileRequestDTO.getReferees());
        }

        if (commonProfileRequestDTO.getResearches() == null || !commonProfileRequestDTO.getResearches().isEmpty()) {
            log.info("Researches are exists.");
            candidateCommonProfileDraft.setResearches(commonProfileRequestDTO.getResearches());
        }

        if (commonProfileRequestDTO.getJobPreferences() == null || !commonProfileRequestDTO.getJobPreferences().isEmpty()) {
            log.info("JobPreferences are exists.");
            candidateCommonProfileDraft.setJobPreferences(commonProfileRequestDTO.getJobPreferences());
        }

        if (commonProfileRequestDTO.getLanguageProficiencies() == null || !commonProfileRequestDTO.getLanguageProficiencies().isEmpty()) {
            log.info("Language proficiencies are exists.");
            candidateCommonProfileDraft.setLanguageProficiencies(commonProfileRequestDTO.getLanguageProficiencies());
        }

        if (commonProfileRequestDTO.getSchoolEducations() == null || !commonProfileRequestDTO.getSchoolEducations().isEmpty()) {
            log.info("School educations are exists.");
            candidateCommonProfileDraft.setSchoolEducations(commonProfileRequestDTO.getSchoolEducations());
        }

        if (commonProfileRequestDTO.getPreferredJobLocations() != null) {
            log.info("Preferred jobLocations are exists.");
            candidateCommonProfileDraft.setPreferredJobLocations(commonProfileRequestDTO.getPreferredJobLocations());
        }

        if (commonProfileRequestDTO.getDocumentDetails() == null || !commonProfileRequestDTO.getDocumentDetails().isEmpty()) {
            log.info("Document details are exists.");

            if (commonProfileRequestDTO.getDocumentDetails() == null) {

                candidateCommonProfileDraft.setDocumentDetails(new ArrayList<>());

            } else {

                mapCommonProfileDocuments(commonProfileRequestDTO.getDocumentDetails(), documentList);
                candidateCommonProfileDraft.setDocumentDetails(commonProfileRequestDTO.getDocumentDetails());

            }

        }

        log.info("CandidateCommonProfileDraftMapper.toEntity() => ended.");
        return candidateCommonProfileDraft;

    }

    /**
     * This method is allowed to set document as a binary file to document details request
     *
     * @param documentDetails {@link List<CommonProfileDocumentRequest>}
     * @param documentList    {@link MultipartFile[]}
     * @author @Maleesha99
     */
    private void mapCommonProfileDocuments(List<CommonProfileDocumentRequest> documentDetails,
                                           MultipartFile[] documentList) {
        log.info("CandidateCommonProfileDraftMapper.mapDocuments() => started.");

        try {

            for (CommonProfileDocumentRequest documentDetail : documentDetails) {

                boolean isDocumentFileFound = false;

                for (MultipartFile file : documentList) {

                    // Find matching document in document list by document name
                    if (Objects.equals(file.getOriginalFilename(), documentDetail.getActualFileName())) {
                        isDocumentFileFound = true;
                        documentDetail.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
                        break;
                    }

                }

                if (!isDocumentFileFound) {
                    throw new BaseException(500, "Document file not found. document name - " + documentDetail.getActualFileName());
                }

            }

        } catch (Exception e) {
            log.warn("Draft occurred an exception. Error - {} ", e.getMessage());

            throw new BaseException(500, "Draft occurred an exception. Error - " + e.getLocalizedMessage());

        }

        log.info("CandidateCommonProfileDraftMapper.mapDocuments() => ended.");

    }

}

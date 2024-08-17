package com.pdev.rempms.candidateservice.service.validation;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CandidateProfileValidation {

    public void validateCandidateCommonProfile(CommonProfileRequestDTO dto) {

        if (dto.getPersonalDetail() == null) {
            log.warn("Personal details are required.");

            throw new BaseException(422, "Personal details are required.");

        } else if (dto.getProfessionalExperiences().isEmpty()) {
            log.warn("Professional experiences are required.");

            throw new BaseException(422, "Professional experiences are required.");

        } else if (dto.getResearches().isEmpty()) {
            log.warn("Researches are required.");

            throw new BaseException(422, "Researches are required.");

        } else if (dto.getMemberships().isEmpty()) {
            log.warn("Memberships are required.");

            throw new BaseException(422, "Memberships are required.");

        } else if (dto.getFamilyInformation().isEmpty()) {
            log.warn("Family information are required.");

            throw new BaseException(422, "Family information are required.");

        } else if (dto.getJobPreferences().isEmpty()) {
            log.warn("Job preferences are required.");

            throw new BaseException(422, "Job preferences are required.");

        } else if (dto.getAchievements() == null) {
            log.warn("Achievements are required.");

            throw new BaseException(422, "Achievements are required.");

        } else if (dto.getSchoolEducations() == null) {
            log.warn("School education are required.");

            throw new BaseException(422, "School education are required.");

        } else if (dto.getPreferredJobLocations() == null) {
            log.warn("Preferred job location are required.");

            throw new BaseException(422, "Preferred job location are required.");

        } else if (dto.getLanguageProficiencies() == null) {
            log.warn("Language proficiencies are required.");

            throw new BaseException(422, "Language proficiencies are required.");

        } else if (dto.getHigherEducations() == null) {
            log.warn("Higher education are required.");

            throw new BaseException(422, "Higher education are required.");

        } else if (dto.getDocumentDetails() == null) {
            log.warn("Document details are required.");

            throw new BaseException(422, "Document details are required.");

        }

    }

}

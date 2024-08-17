package com.pdev.rempms.candidateservice.mapper.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.CandidateDTO;
import com.pdev.rempms.candidateservice.dto.candidate.CandidateSearchLazyResponseDTO;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.achievement.Achievement;
import com.pdev.rempms.candidateservice.model.candidate.cvOrCertificate.Document;
import com.pdev.rempms.candidateservice.model.candidate.familyInformation.FamilyInformation;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEducation;
import com.pdev.rempms.candidateservice.model.candidate.jobPreference.JobPreference;
import com.pdev.rempms.candidateservice.model.candidate.languageProfficiency.LanguageProficiency;
import com.pdev.rempms.candidateservice.model.candidate.member.Membership;
import com.pdev.rempms.candidateservice.model.candidate.personalDetail.PersonalDetail;
import com.pdev.rempms.candidateservice.model.candidate.prefferedJobLocation.PreferredJobLocation;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.ProfessionalExperience;
import com.pdev.rempms.candidateservice.model.candidate.referee.Referee;
import com.pdev.rempms.candidateservice.model.candidate.research.Research;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.SchoolEducation;
import com.pdev.rempms.candidateservice.util.CommonValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class CandidateMapper {

    public Candidate toEntity(Candidate candidate, CandidateDTO dto, String refNo) {
        log.info("CandidateMapper -> toEntity() => started!");
        if (!CommonValidation.integerNullValidation(dto.getIdCandidate())) {
            log.info("CandidateMapper -> toEntity() => candidate exists!");
            candidate.setId(dto.getIdCandidate());
        }
        candidate.setCandidateNo(refNo);
        candidate.setUserAccount(dto.getIdUserAccount());
        candidate.setCommonStatus(dto.getCommonStatus());
        log.info("CandidateMapper -> toEntity() => ended!");
        return candidate;
    }

    public CandidateDTO toDto(CandidateDTO candidateDTO, Candidate createdCandidate) {
        log.info("CandidateMapper -> toDto() => started!");
        candidateDTO.setIdCandidate(createdCandidate.getId());
        candidateDTO.setCandidateNo(createdCandidate.getCandidateNo());
        candidateDTO.setIdUserAccount(createdCandidate.getUserAccount());
        candidateDTO.setCommonStatus(createdCandidate.getCommonStatus());
        candidateDTO.setIsVerify(createdCandidate.getIsVerify());
        log.info("CandidateMapper -> toDto() => ended!");
        return candidateDTO;
    }

    public CandidateSearchLazyResponseDTO toLazyResponseDTO(CandidateSearchLazyResponseDTO dto, Candidate candidate) {
        log.info("CandidateMapper -> toLazyResponseDTO() => started!");

        dto.setCandidateId(candidate.getId());
        dto.setCandidateNo(candidate.getCandidateNo());

        dto.setFullName(candidate.getPersonalDetail() == null ? "-" :
                candidate.getPersonalDetail().getFirstName().concat(" ").concat(candidate.getPersonalDetail().getLastName()));

        dto.setNic(candidate.getPersonalDetail() == null ? "-" : candidate.getPersonalDetail().getNic());
        
        dto.setNoticePeriod(candidate.getPersonalDetail() == null ? "-" : candidate.getPersonalDetail().getNoticePeriod());

        dto.setExpectedSalary(candidate.getPersonalDetail() == null ? BigDecimal.ZERO :
                candidate.getPersonalDetail().getExpectedSalary());

        log.info("CandidateMapper -> toLazyResponseDTO() => ended!");
        return dto;

    }

    public void toEntity(Candidate candidate, PersonalDetail personalDetail, List<ProfessionalExperience> professionalExperienceList,
                         List<HigherEducation> higherEducationList, List<SchoolEducation> schoolEducations,
                         List<Membership> membershipList, List<LanguageProficiency> languageProficiencyList,
                         List<Research> researchList, Achievement achievement, List<Referee> refereeList,
                         List<FamilyInformation> familyInformationList, List<JobPreference> jobPreferenceList,
                         List<PreferredJobLocation> preferredJobLocationList, List<Document> documentList) {

        candidate.setPersonalDetail(personalDetail);
        candidate.setProfessionalExperienceList(professionalExperienceList);
        candidate.setAchievement(achievement);
        candidate.setFamilyInformationList(familyInformationList);
        candidate.setHigherEducationList(higherEducationList);
        candidate.setSchoolEducationList(schoolEducations);
        candidate.setJobPreferenceList(jobPreferenceList);
        candidate.setLanguageProficiencyList(languageProficiencyList);
        candidate.setMembershipList(membershipList);
        candidate.setRefereeList(refereeList);
        candidate.setResearchList(researchList);
        candidate.setPreferredJobLocations(preferredJobLocationList);
        candidate.setDocumentList(documentList);

    }

}

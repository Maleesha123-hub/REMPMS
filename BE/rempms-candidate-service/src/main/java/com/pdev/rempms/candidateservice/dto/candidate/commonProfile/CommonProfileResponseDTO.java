package com.pdev.rempms.candidateservice.dto.candidate.commonProfile;

import com.pdev.rempms.candidateservice.dto.candidate.achievments.AchievementDTO;
import com.pdev.rempms.candidateservice.dto.candidate.document.DocumentDTO;
import com.pdev.rempms.candidateservice.dto.candidate.familyInformation.FamilyInformationDTO;
import com.pdev.rempms.candidateservice.dto.candidate.higherEducation.HigherEducationResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.jobPreference.JobPreferenceResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.languageProficiency.LanguageProficiencyResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.membership.MembershipResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.personalDetail.PersonalDetailResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.preferredJobLocation.PreferredJobLocationResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.professionalExperience.ProfessionalExperienceResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.referee.RefereeDTO;
import com.pdev.rempms.candidateservice.dto.candidate.research.ResearchResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.schoolEducation.SchoolEducationResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/04/09
 */
@Getter
@Setter
public class CommonProfileResponseDTO {

    private Integer idCandidate;
    private String candidateNo;
    private PersonalDetailResponseDTO personalDetail;
    private List<ProfessionalExperienceResponseDTO> professionalExperiences;
    private List<HigherEducationResponseDTO> higherEducations;
    private List<SchoolEducationResponseDTO> schoolEducations;
    private List<MembershipResponseDTO> memberships;
    private List<LanguageProficiencyResponseDTO> languageProficiencies;
    private List<ResearchResponseDTO> researches;
    private AchievementDTO achievements;
    private List<RefereeDTO> referees;
    private List<FamilyInformationDTO> familyInformation;
    private List<JobPreferenceResponseDTO> jobPreferences;
    private List<PreferredJobLocationResponseDTO> preferredJobLocations;
    private List<DocumentDTO> documentDetails;

}

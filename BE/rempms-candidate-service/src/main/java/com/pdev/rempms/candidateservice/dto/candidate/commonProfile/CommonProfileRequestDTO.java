package com.pdev.rempms.candidateservice.dto.candidate.commonProfile;

import com.pdev.rempms.candidateservice.dto.candidate.achievments.AchievementDTO;
import com.pdev.rempms.candidateservice.dto.candidate.document.DocumentDTO;
import com.pdev.rempms.candidateservice.dto.candidate.familyInformation.FamilyInformationDTO;
import com.pdev.rempms.candidateservice.dto.candidate.higherEducation.HigherEducationRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.jobPreference.JobPreferenceRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.languageProficiency.LanguageProficiencyRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.membership.MembershipRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.personalDetail.PersonalDetailRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.preferredJobLocation.PreferredJobLocationRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.professionalExperience.ProfessionalExperienceRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.referee.RefereeDTO;
import com.pdev.rempms.candidateservice.dto.candidate.research.ResearchRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.schoolEducation.SchoolEducationRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class CommonProfileRequestDTO {

    private Integer idCandidate;
    private PersonalDetailRequestDTO personalDetail;
    private List<ProfessionalExperienceRequestDTO> professionalExperiences;
    private List<HigherEducationRequestDTO> higherEducations;
    private List<SchoolEducationRequestDTO> schoolEducations;
    private List<MembershipRequestDTO> memberships;
    private List<LanguageProficiencyRequestDTO> languageProficiencies;
    private List<ResearchRequestDTO> researches;
    private AchievementDTO achievements;
    private List<RefereeDTO> referees;
    private List<FamilyInformationDTO> familyInformation;
    private List<JobPreferenceRequestDTO> jobPreferences;
    private List<PreferredJobLocationRequestDTO> preferredJobLocations;
    private List<DocumentDTO> documentDetails;

}

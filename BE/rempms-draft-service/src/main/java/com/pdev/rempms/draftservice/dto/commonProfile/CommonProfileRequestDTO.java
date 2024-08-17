package com.pdev.rempms.draftservice.dto.commonProfile;

import com.pdev.rempms.draftservice.dto.achievments.AchievementDTO;
import com.pdev.rempms.draftservice.dto.document.CommonProfileDocumentRequest;
import com.pdev.rempms.draftservice.dto.familyInformation.FamilyInformationDTO;
import com.pdev.rempms.draftservice.dto.higherEducation.HigherEducationDTO;
import com.pdev.rempms.draftservice.dto.jobPreference.JobPreferenceDTO;
import com.pdev.rempms.draftservice.dto.languageProficiency.LanguageProficiencyDTO;
import com.pdev.rempms.draftservice.dto.membership.MembershipDTO;
import com.pdev.rempms.draftservice.dto.personalDetail.PersonalDetailDTO;
import com.pdev.rempms.draftservice.dto.preferredJobLocation.PreferredJobLocationDTO;
import com.pdev.rempms.draftservice.dto.professionalExperience.ProfessionalExperienceDTO;
import com.pdev.rempms.draftservice.dto.referee.RefereeDTO;
import com.pdev.rempms.draftservice.dto.research.ResearchDTO;
import com.pdev.rempms.draftservice.dto.schoolEducation.SchoolEducationDTO;
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

    private Long id;
    private Integer idCandidate;
    private PersonalDetailDTO personalDetail;
    private List<ProfessionalExperienceDTO> professionalExperiences;
    private List<HigherEducationDTO> higherEducations;
    private List<SchoolEducationDTO> schoolEducations;
    private List<MembershipDTO> memberships;
    private List<LanguageProficiencyDTO> languageProficiencies;
    private List<ResearchDTO> researches;
    private AchievementDTO achievements;
    private List<RefereeDTO> referees;
    private List<FamilyInformationDTO> familyInformation;
    private List<JobPreferenceDTO> jobPreferences;
    private PreferredJobLocationDTO preferredJobLocations;
    private List<CommonProfileDocumentRequest> documentDetails;

}

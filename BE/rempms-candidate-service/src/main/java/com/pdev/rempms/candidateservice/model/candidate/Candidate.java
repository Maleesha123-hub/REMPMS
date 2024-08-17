package com.pdev.rempms.candidateservice.model.candidate;

import com.pdev.rempms.candidateservice.model.AuditData;
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
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 31/12/2023
 */
@Entity
@Getter
@Setter
@Table(name = "candidate")
public class Candidate {
    @Id
    @Column(name = "id_candidate")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "candidate_no") // should auto generate
    private String candidateNo;

    @Column(name = "user_account_id_user_account") // After creating user account during the sign up - get that id
    private Integer userAccount; // user-service

    @Column(name = "common_status")
    @Length(max = 45)
    private String commonStatus;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

    @Column(name = "is_verify")
    private Boolean isVerify;

    @OneToOne(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PersonalDetail personalDetail;

    @OneToOne(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Achievement achievement;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProfessionalExperience> professionalExperienceList;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HigherEducation> higherEducationList;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SchoolEducation> schoolEducationList;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Membership> membershipList;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LanguageProficiency> languageProficiencyList;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Research> researchList;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Referee> refereeList;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FamilyInformation> familyInformationList;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JobPreference> jobPreferenceList;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PreferredJobLocation> preferredJobLocations;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Document> documentList;

}

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

import java.util.ArrayList;
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

    @Column(name = "draft_id_draft", nullable = false) // should auto generate
    private Integer idDraft;

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

    @OneToOne(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private PersonalDetail personalDetail;

    @OneToOne(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private Achievement achievement;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<ProfessionalExperience> professionalExperienceList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<HigherEducation> higherEducationList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<SchoolEducation> schoolEducationList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Membership> membershipList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<LanguageProficiency> languageProficiencyList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Research> researchList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Referee> refereeList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<FamilyInformation> familyInformationList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<JobPreference> jobPreferenceList = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<PreferredJobLocation> preferredJobLocations = new ArrayList<>();

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Document> documentList = new ArrayList<>();

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", candidateNo='" + candidateNo + '\'' +
                ", idDraft=" + idDraft +
                ", userAccount=" + userAccount +
                ", commonStatus='" + commonStatus + '\'' +
                ", auditData=" + auditData +
                ", isVerify=" + isVerify +
                '}';
    }
}

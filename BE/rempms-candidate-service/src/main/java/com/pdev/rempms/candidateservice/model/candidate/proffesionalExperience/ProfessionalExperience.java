package com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience;

import com.pdev.rempms.candidateservice.model.AuditData;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author maleeshasa
 * @Date 31/12/2023
 */
@Entity
@Getter
@Setter
@Table(name = "professional_experience")
public class ProfessionalExperience {
    @Id
    @Column(name = "id_professional_experience")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "organization")
    private String organization;

    @Column(name = "designation")
    private String designation;

    @Column(name = "commenced_date")
    private LocalDate commencedDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "industry_id_industry")
    private Industry industry;

    @ManyToOne
    @JoinColumn(name = "jobCategory_id_jobCategory")
    private JobCategory jobCategory;

    @Column(name = "still_working")
    private Boolean stillWorking;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

    @JoinColumn(name = "candidate_id_candidate")
    @ManyToOne(fetch = FetchType.LAZY)
    private Candidate candidate;

    @Override
    public String toString() {
        return "ProfessionalExperience{" +
                "id=" + id +
                ", organization='" + organization + '\'' +
                ", designation='" + designation + '\'' +
                ", commencedDate=" + commencedDate +
                ", completionDate=" + completionDate +
                ", description='" + description + '\'' +
                ", industry=" + industry +
                ", jobCategory=" + jobCategory +
                ", stillWorking=" + stillWorking +
                ", auditData=" + auditData +
                // Exclude candidate to prevent recursion
                '}';
    }
}

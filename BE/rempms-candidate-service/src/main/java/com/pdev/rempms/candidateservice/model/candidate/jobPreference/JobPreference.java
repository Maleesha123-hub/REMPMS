package com.pdev.rempms.candidateservice.model.candidate.jobPreference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdev.rempms.candidateservice.model.AuditData;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.Industry;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.JobCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author maleeshasa
 * @Date 31/12/2023
 */
@Entity
@Getter
@Setter
@Table(name = "job_preference")
public class JobPreference {
    @Id
    @Column(name = "id_job_preference")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "industry_id_industry")
    private Industry industry;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobCategory_id_jobCategory")
    private JobCategory jobCategory;

    @Column(name = "preference")
    private String preference; // hardcoded values : 1,2,3,4,5

    @Column(name = "remark")
    private String remark;

    @Column(name = "preferred_ommunication_id_preferred_ommunication")
    private Integer idPreferredCommunication; //communication-service

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

    @JoinColumn(name = "candidate_id_candidate")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Candidate candidate;
}

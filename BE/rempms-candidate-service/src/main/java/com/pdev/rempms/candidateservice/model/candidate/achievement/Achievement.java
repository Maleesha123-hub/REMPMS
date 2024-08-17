package com.pdev.rempms.candidateservice.model.candidate.achievement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdev.rempms.candidateservice.model.AuditData;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
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
@Table(name = "achievement")
public class Achievement {

    @Id
    @Column(name = "id_achievement")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "achievements")
    private String achievements;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

    @JoinColumn(name = "candidate_id_candidate")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Candidate candidate;

}

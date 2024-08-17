package com.pdev.rempms.candidateservice.model.candidate.research;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdev.rempms.candidateservice.model.AuditData;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import jakarta.persistence.*;
import lombok.Data;
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
@Table(name = "research")
public class Research {
    @Id
    @Column(name = "id_research")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "commenced_date")
    private LocalDate commencedDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "research_area_id_research_area")
    private ResearchArea researchArea;

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

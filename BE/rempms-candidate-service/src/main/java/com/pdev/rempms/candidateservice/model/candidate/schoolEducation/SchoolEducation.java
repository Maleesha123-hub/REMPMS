package com.pdev.rempms.candidateservice.model.candidate.schoolEducation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdev.rempms.candidateservice.model.AuditData;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author maleeshasa
 * @Date 31/12/2023
 */
@Entity
@Getter
@Setter
@Table(name = "school_education")
public class SchoolEducation {
    @Id
    @Column(name = "id_school_education")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "school_edu_qualification")
    private String schoolEduQualification; //enum : school qualification

    @Column(name = "school")
    private String school; //school / institute

    @Column(name = "achieved_on")
    private LocalDate achievedOn;

    @Column(name = "language_id_language") //medium
    private Integer idLanguage; //communication-service

    @Column(name = "country_id_country")
    private Integer idCountry; //location-service

    @Column(name = "description")
    private String description;

    @JoinColumn(name = "scheme_id_scheme")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Scheme scheme;

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

    @OneToMany(mappedBy = "schoolEducation", fetch = FetchType.LAZY)
    private List<SubjectHasSchoolEduGrade> subjectHasSchoolEduGrades = new ArrayList<>();

}

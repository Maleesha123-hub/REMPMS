package com.pdev.rempms.candidateservice.model.candidate.higherEducation;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "higher_education")
public class HigherEducation {
    @Id
    @Column(name = "id_higher_education")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "institute_of_study")
    private String instituteOfStudy;

    @Column(name = "affiliated_institute")
    private String affiliatedInstitute;

    @Column(name = "award_type")
    private String awardType; //enum

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qualification_id_qualification")
    private HigherEduQualification higherEduQualification;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_of_study_id_area_of_study")
    private AreaOfStudy areaOfStudy;

    @Column(name = "commenced_date")
    private LocalDate commencedDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "result")
    private String result;

    @Column(name = "country_id_country")
    private Integer idCountry; //location-service

    @Column(name = "language_id_language") //medium
    private Integer idLanguage; //communication-service

    @Column(name = "description")
    private String description;

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

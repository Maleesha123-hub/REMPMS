package com.pdev.rempms.candidateservice.model.candidate.schoolEducation;

import com.pdev.rempms.candidateservice.model.AuditData;
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
@Table(name = "subject_has_school_edu_grade")
public class SubjectHasSchoolEduGrade {

    @Id
    @Column(name = "id_subject_has_school_edu_grade")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "subject_id_subject")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Subject subject;

    @JoinColumn(name = "school_education_id_school_education")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SchoolEducation schoolEducation;

    @Column(name = "grade")
    private String grade; //enum : grade

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;
}

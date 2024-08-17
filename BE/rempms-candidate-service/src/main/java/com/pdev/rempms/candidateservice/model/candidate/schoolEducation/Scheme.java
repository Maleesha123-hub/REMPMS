package com.pdev.rempms.candidateservice.model.candidate.schoolEducation;

import com.pdev.rempms.candidateservice.model.AuditData;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author maleeshasa
 * @Date 29/12/2023
 */
@Entity
@Getter
@Setter
@Table(name = "scheme")
public class Scheme {
    @Id
    @Column(name = "id_scheme")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "scheme_name")
    private String schemeName;

    @Column(name = "school_edu_qualification")
    private String schoolEduQualification; //enum - school qualification

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;
}

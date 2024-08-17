package com.pdev.rempms.candidateservice.model.candidate.higherEducation;

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
@Table(name = "area_of_study")
public class AreaOfStudy {
    @Id
    @Column(name = "id_area_of_study")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "area_of_study_name")
    private String name;

    @Column(name = "area_of_study_description")
    private String description;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;
}

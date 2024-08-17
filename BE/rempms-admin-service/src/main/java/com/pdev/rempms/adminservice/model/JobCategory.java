package com.pdev.rempms.adminservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
@Entity
@Data
@Table(name = "job_category")
public class JobCategory {
    @Id
    @Column(name = "id_job_category")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_category_name")
    @Length(max = 45)
    private String categoryName;

    @Column(name = "job_category_description")
    @Length(max = 45)
    private String description;

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
}

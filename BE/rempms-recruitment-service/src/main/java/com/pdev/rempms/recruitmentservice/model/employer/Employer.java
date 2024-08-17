package com.pdev.rempms.recruitmentservice.model.employer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pdev.rempms.recruitmentservice.model.AuditData;
import com.pdev.rempms.recruitmentservice.model.jobVacancy.JobVacancy;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "employer")
public class Employer {
    @Id
    @Column(name = "id_employer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employer_name", nullable = false)
    @Length(max = 45)
    private String employerName;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone_no")
    private String telephoneNo;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "employer_no")
    private String employerNo;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "employer", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<JobVacancy> jobVacancies = new ArrayList<>();
}

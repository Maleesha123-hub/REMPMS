package com.pdev.rempms.recruitmentservice.model.jobVacancy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pdev.rempms.recruitmentservice.listeners.VacancyEntityListener;
import com.pdev.rempms.recruitmentservice.model.AuditData;
import com.pdev.rempms.recruitmentservice.model.vacancyHasCandidates.VacancyHasCandidates;
import com.pdev.rempms.recruitmentservice.model.employer.Employer;
import com.pdev.rempms.recruitmentservice.model.jobPosition.JobPosition;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "job_vacancy")
@EntityListeners(VacancyEntityListener.class)
public class JobVacancy {
    @Id
    @Column(name = "id_job_vacancy")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description")
    @Length(max = 45)
    private String description;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @Column(name = "is_govt_job")
    private boolean govtJob;

    @Column(name = "is_walks_in_interview")
    private boolean walksInInterview;

    @Column(name = "is_part_time")
    private boolean partTime;

    @Column(name = "active")
    private boolean active;

    @Column(name = "poster_location_url")
    private String posterUrl;

    @Column(name = "poster_saved_name")
    private String posterName;

    @Column(name = "reference_number")
    private String refNo;

    @JoinColumn(name = "employer_id_employer")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Manage serialization
    @ToString.Exclude
    private Employer employer;

    @JoinColumn(name = "job_position_id_job_position")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private JobPosition jobPosition;

    @OneToMany(mappedBy = "jobVacancy", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VacancyHasCandidates> vacancyHasCandidates;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

}

package com.pdev.rempms.recruitmentservice.model.vacancyHasCandidates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdev.rempms.recruitmentservice.model.jobVacancy.JobVacancy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vacancy_has_candidates")
public class VacancyHasCandidates {

    @Id
    @Column(name = "id_vacancy_has_candidates")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "email")
    private String email;

    @Column(name = "cv_url")
    private String cvUrl;

    @Column(name = "candidate_id_candidate", nullable = false)
    private Integer candidateId;

    @JoinColumn(name = "job_vacancy_id_job_vacancy", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private JobVacancy jobVacancy;
}

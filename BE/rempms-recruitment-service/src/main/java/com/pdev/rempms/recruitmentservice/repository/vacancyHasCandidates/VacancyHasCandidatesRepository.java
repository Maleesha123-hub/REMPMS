package com.pdev.rempms.recruitmentservice.repository.vacancyHasCandidates;

import com.pdev.rempms.recruitmentservice.model.jobVacancy.JobVacancy;
import com.pdev.rempms.recruitmentservice.model.vacancyHasCandidates.VacancyHasCandidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyHasCandidatesRepository extends JpaRepository<VacancyHasCandidates, Integer> {

    List<VacancyHasCandidates> findByJobVacancy(JobVacancy jobVacancy);

}

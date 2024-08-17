package com.pdev.rempms.candidateservice.repository;

import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author @Maleesha99
 * @Date 20224/02/04
 */
@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Integer> {
}

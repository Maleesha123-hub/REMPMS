package com.pdev.rempms.candidateservice.repository;

import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEduQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author @Maleesha99
 * @Date 2024/02/04
 */
@Repository
public interface HigherEduQualificationRepository extends JpaRepository<HigherEduQualification, Integer> {
}

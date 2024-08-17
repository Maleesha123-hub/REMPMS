package com.pdev.rempms.candidateservice.repository;

import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.SubjectHasSchoolEduGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author @Maleesha99
 * @Date 2024/02/05
 */
@Repository
public interface SubjectHasSchoolEduGradeRepository extends JpaRepository<SubjectHasSchoolEduGrade, Integer> {
}

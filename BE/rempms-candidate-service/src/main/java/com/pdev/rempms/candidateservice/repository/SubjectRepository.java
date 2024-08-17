package com.pdev.rempms.candidateservice.repository;

import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.Scheme;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/05
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    List<Subject> findBySchoolEduQualification(String schoolEduQualification);

    List<Subject> findBySchemeAndSchoolEduQualification(Scheme scheme, String schoolEduQualification);

}

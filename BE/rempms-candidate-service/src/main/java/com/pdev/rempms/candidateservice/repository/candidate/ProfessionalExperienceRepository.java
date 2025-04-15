package com.pdev.rempms.candidateservice.repository.candidate;

import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.ProfessionalExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Repository
public interface ProfessionalExperienceRepository extends JpaRepository<ProfessionalExperience, Integer> {

    void deleteByCandidate(Candidate candidate);

    List<ProfessionalExperience> findByCandidate(Candidate candidate);
}

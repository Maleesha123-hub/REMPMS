package com.pdev.rempms.candidateservice.repository.candidate;

import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer>, JpaSpecificationExecutor<Candidate> {

    /**
     * This method is allowed to get the candidate by id desc
     *
     * @return {@link Optional<Candidate>} - Candidate
     */
    Optional<Candidate> findTopByOrderByIdDesc();

}

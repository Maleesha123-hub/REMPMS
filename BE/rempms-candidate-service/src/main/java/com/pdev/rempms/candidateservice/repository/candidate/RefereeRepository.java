package com.pdev.rempms.candidateservice.repository.candidate;

import com.pdev.rempms.candidateservice.model.candidate.referee.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {
}

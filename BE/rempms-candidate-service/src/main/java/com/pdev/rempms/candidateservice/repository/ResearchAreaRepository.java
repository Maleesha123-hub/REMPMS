package com.pdev.rempms.candidateservice.repository;

import com.pdev.rempms.candidateservice.model.candidate.research.ResearchArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author @Maleesha99
 * @Date 2024/02/09
 */
@Repository
public interface ResearchAreaRepository extends JpaRepository<ResearchArea, Integer> {
}

package com.pdev.rempms.candidateservice.repository;

import com.pdev.rempms.candidateservice.model.candidate.prefferedJobLocation.JobLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
@Repository
public interface JobLocationRepository extends JpaRepository<JobLocation, Integer> {
}

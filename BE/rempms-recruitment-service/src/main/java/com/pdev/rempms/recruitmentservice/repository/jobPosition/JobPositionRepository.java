package com.pdev.rempms.recruitmentservice.repository.jobPosition;

import com.pdev.rempms.recruitmentservice.model.jobPosition.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Integer> {
    List<JobPosition> findByActive(Boolean aTrue);
}

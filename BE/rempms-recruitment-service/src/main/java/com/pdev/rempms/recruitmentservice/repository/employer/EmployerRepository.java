package com.pdev.rempms.recruitmentservice.repository.employer;

import com.pdev.rempms.recruitmentservice.model.employer.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer> {

    List<Employer> findByActive(boolean active);

    Optional<Employer> findTopByOrderByIdDesc();

}

package com.pdev.rempms.adminservice.repository;

import com.pdev.rempms.adminservice.model.JobCategory;
import com.pdev.rempms.adminservice.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {

    JobCategory findByIdAndCommonStatus(Long id, String status);

    List<JobCategory> findByCommonStatus(String status);
}

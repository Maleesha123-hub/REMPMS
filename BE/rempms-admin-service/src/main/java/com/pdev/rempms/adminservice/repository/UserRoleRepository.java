package com.pdev.rempms.adminservice.repository;

import com.pdev.rempms.adminservice.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByIdAndCommonStatus(Long id, String status);
}

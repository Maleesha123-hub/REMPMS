package com.pdev.rempms.adminservice.repository;

import com.pdev.rempms.adminservice.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByIdAndCommonStatus(Long id, String status);

    List<UserAccount> findByCommonStatus(String status);
}

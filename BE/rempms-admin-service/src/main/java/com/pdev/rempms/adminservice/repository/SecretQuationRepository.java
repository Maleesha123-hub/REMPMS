package com.pdev.rempms.adminservice.repository;

import com.pdev.rempms.adminservice.model.SecretQuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 29/12/2023
 */
@Repository
public interface SecretQuationRepository extends JpaRepository<SecretQuation, Long> {

    SecretQuation findByIdAndCommonStatus(Long id, String status);

    List<SecretQuation> findByCommonStatus(String status);
}

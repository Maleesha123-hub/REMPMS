package com.pdev.rempms.communicationservice.repository;

import com.pdev.rempms.communicationservice.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    Language getLanguageByIdAndCommonStatus(Long id, String status);
}

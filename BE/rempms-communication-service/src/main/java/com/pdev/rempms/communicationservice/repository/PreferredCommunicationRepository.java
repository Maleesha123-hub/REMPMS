package com.pdev.rempms.communicationservice.repository;

import com.pdev.rempms.communicationservice.model.PreferredCommunication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Repository
public interface PreferredCommunicationRepository extends JpaRepository<PreferredCommunication, Long> {

    PreferredCommunication getPreferredCommunicationByIdAndCommonStatus(Long id, String status);
}

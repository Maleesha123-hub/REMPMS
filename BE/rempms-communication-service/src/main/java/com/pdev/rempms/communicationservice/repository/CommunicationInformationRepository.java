package com.pdev.rempms.communicationservice.repository;

import com.pdev.rempms.communicationservice.model.CommunicationInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Repository
public interface CommunicationInformationRepository extends JpaRepository<CommunicationInformation, Long> {

    CommunicationInformation getCommunicationInformationByIdAndCommonStatus(Long id, String status);
}

package com.pdev.rempms.locationservice.repository;

import com.pdev.rempms.locationservice.model.LocationInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Repository
public interface LocationInformationRepository extends JpaRepository<LocationInformation, Long> {
}

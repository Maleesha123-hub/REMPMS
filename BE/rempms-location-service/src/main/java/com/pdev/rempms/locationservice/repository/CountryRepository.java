package com.pdev.rempms.locationservice.repository;

import com.pdev.rempms.locationservice.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findCountryByIdAndCommonStatus(Long id, String status);
}

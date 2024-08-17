package com.pdev.rempms.locationservice.repository;

import com.pdev.rempms.locationservice.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

    Province findProvinceByIdAndCommonStatus(Long id, String status);

    List<Province> findProvinceByCountryIdAndCommonStatus(Long idCountry, String status);
}

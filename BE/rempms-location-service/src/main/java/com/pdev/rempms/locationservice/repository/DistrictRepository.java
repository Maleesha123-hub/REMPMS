package com.pdev.rempms.locationservice.repository;

import com.pdev.rempms.locationservice.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    List<District> findDistrictByProvinceIdAndCountryId(Long idProvince, Long idCountry);

    District findDistrictByIdAndCommonStatus(Long idDistrict, String value);
}

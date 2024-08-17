package com.pdev.rempms.locationservice.repository;

import com.pdev.rempms.locationservice.model.City;
import com.pdev.rempms.locationservice.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findCityByIdAndCommonStatus(Long id, String status);

    List<City> findCityByCountryIdAndProvinceIdAndDistrictId(Long idCountry, Long idProvince, Long idDistrict);
}

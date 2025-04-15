package com.pdev.rempms.locationservice.service.impl.city;

import com.pdev.rempms.locationservice.enums.CommonStatus;
import com.pdev.rempms.locationservice.constants.validation.CityValidationMessage;
import com.pdev.rempms.locationservice.constants.validation.CommonValidationMessage;
import com.pdev.rempms.locationservice.dto.city.CityDTO;
import com.pdev.rempms.locationservice.exception.RecordNotFoundException;
import com.pdev.rempms.locationservice.mapper.city.CityMapper;
import com.pdev.rempms.locationservice.model.*;
import com.pdev.rempms.locationservice.repository.CityRepository;
import com.pdev.rempms.locationservice.repository.CountryRepository;
import com.pdev.rempms.locationservice.repository.DistrictRepository;
import com.pdev.rempms.locationservice.repository.ProvinceRepository;
import com.pdev.rempms.locationservice.service.city.CityService;
import com.pdev.rempms.locationservice.util.CommonResponse;
import com.pdev.rempms.locationservice.util.CommonUtil;
import com.pdev.rempms.locationservice.util.CommonValidation;
import com.pdev.rempms.locationservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    private final CommonUtil commonUtil;
    private final CityMapper cityMapper;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final CountryRepository countryRepository;
    private final ProvinceRepository provinceRepository;

    /**
     * save or update city
     *
     * @param dto - city saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> saveUpdateCity(CityDTO dto) {
        log.info("CityServiceImpl -> saveUpdateCity() => started!");
        CommonResponse commonResponse = new CommonResponse();
        Country country;
        Province province;
        District district;
        City city = new City();
        String validation = validateCity(dto);
        if (validation != null) {
            log.info("CityServiceImpl -> saveUpdateCity() => Validation available!");
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(validation);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        country = countryRepository.findCountryByIdAndCommonStatus(Long.valueOf(dto.getIdCountry()), CommonStatus.ACTIVE.getValue());
        province = provinceRepository.findProvinceByIdAndCommonStatus(Long.valueOf(dto.getIdProvince()), CommonStatus.ACTIVE.getValue());
        district = districtRepository.findDistrictByIdAndCommonStatus(Long.valueOf(dto.getIdDistrict()), CommonStatus.ACTIVE.getValue());
        if (!CommonValidation.stringNullValidation(dto.getIdCity())) {
            log.info("CityServiceImpl -> saveUpdateCity() => Update existing city!");
            city = cityRepository.findById(Long.valueOf(dto.getIdCity())).get();
            city = cityMapper.toEntity(city, dto, country, province, district);
            city.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            city.getAuditData().setUpdatedBy(commonUtil.getUsername()); //need further development for authorization
        } else {
            log.info("CityServiceImpl -> saveUpdateCity() => Save new city!");
            AuditData auditData = new AuditData(LocalDateTime.now(), commonUtil.getUsername());
            city.setAuditData(auditData);
            city = cityMapper.toEntity(city, dto, country, province, district);
        }
        cityRepository.save(city);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(CityValidationMessage.CITY_SAVED_SUCCESS);
        log.info("CityServiceImpl -> saveUpdateCity() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Validate city save data
     *
     * @param dto - city saving or updating details
     * @return - Validation messages
     * @author maleeshasa
     */
    private String validateCity(CityDTO dto) {
        log.info("CityServiceImpl -> validateCity() => started!");
        String validation = null;
        if (CommonValidation.stringNullValidation(dto.getCityName())) {
            validation = CommonValidationMessage.NAME_EMPTY;
        } else if (dto.getCommonStatus().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COMMON_STATUS_EMPTY;
        } else if (CommonValidation.stringNullValidation(dto.getCityZipCode())) {
            validation = CityValidationMessage.CITY_ZIP_NOT_FOUND;
        } else if (dto.getIdCountry().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COUNTRY_NOT_SELECT;
        } else if (dto.getIdProvince().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.PROVINCE_NOT_SELECT;
        } else if (dto.getIdDistrict().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.DISTRICT_NOT_SELECT;
        }
        log.info("CityServiceImpl -> validateCity() => ended!");
        return validation;
    }

    /**
     * Get active city by id
     *
     * @param idCity - city id
     * @return - Active city data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getActiveCityById(Long idCity) {
        log.info("CityServiceImpl -> getActiveCityById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        City city = cityRepository.findCityByIdAndCommonStatus(idCity, CommonStatus.ACTIVE.getValue());
        if (city != null) {
            log.info("CityServiceImpl -> getActiveCityById() => City found!");
            CityDTO dto = new CityDTO();
            dto = cityMapper.toDto(dto, city);
            commonResponse.setMessage(CityValidationMessage.CITY_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("CityServiceImpl -> getActiveCityById() => City not found!");
            throw new RecordNotFoundException(CityValidationMessage.CITY_NOT_FOUND);
        }
        log.info("CityServiceImpl -> getActiveCityById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all active cities
     *
     * @return - All Active cities data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActiveCities() {
        log.info("CityServiceImpl -> getAllActiveCities() => started.");
        CommonResponse commonResponse = new CommonResponse();
        List<City> cities = cityRepository.findAll();
        if (!cities.isEmpty()) {
            log.info("CityServiceImpl -> getAllActiveCities() => Cities found.");
            List<CityDTO> cityDTOList = cities.stream()
                    .filter(city -> city.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                    .map(city -> {
                        CityDTO dto = new CityDTO();
                        return cityMapper.toDto(dto, city);
                    })
                    .toList();
            commonResponse.setMessage(CityValidationMessage.CITIES_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(cityDTOList);
        } else {
            log.info("CityServiceImpl -> getAllActiveCities() => Cities not found.");
            throw new RecordNotFoundException(CityValidationMessage.CITY_NOT_FOUND);
        }
        log.info("CityServiceImpl -> getAllActiveCities() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete city by city id
     *
     * @param idCity - city id
     * @return - City deleted success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> deleteCityById(Long idCity) {
        log.info("CityServiceImpl -> deleteCityById() => started.");
        CommonResponse commonResponse = new CommonResponse();
        Optional<City> city = cityRepository.findById(idCity);
        if (city.isPresent()) {
            log.info("CityServiceImpl -> deleteCityById() => City found.");
            city.get().setCommonStatus(CommonStatus.DELETED.getValue());
            city.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            city.get().getAuditData().setUpdatedBy(commonUtil.getUsername()); //need further development for authorization
            cityRepository.save(city.get());
            commonResponse.setMessage(CityValidationMessage.CITY_DELETED_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("CityServiceImpl -> deleteCityById() => City not found.");
            throw new RecordNotFoundException(CityValidationMessage.CITY_NOT_FOUND);
        }
        log.info("CityServiceImpl -> deleteCityById() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get cities by country, province and district
     *
     * @param idCountry  - country id
     * @param idProvince - province id
     * @param idDistrict - district id
     * @return - Cities  success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getByIdCountryProvinceAndDistrict(Long idCountry, Long idProvince, Long idDistrict) {
        log.info("CityServiceImpl -> getByIdCountryProvinceAndDistrict() => started.");
        CommonResponse commonResponse = new CommonResponse();
        List<City> cities = cityRepository.findCityByCountryIdAndProvinceIdAndDistrictId(idCountry, idProvince, idDistrict);
        if (!cities.isEmpty()) {
            log.info("CityServiceImpl -> getByIdCountryProvinceAndDistrict() => Cities found.");
            List<CityDTO> cityDTOList = cities.stream()
                    .map(city -> {
                        CityDTO dto = new CityDTO();
                        return cityMapper.toDto(dto, city);
                    }).toList();
            commonResponse.setMessage(CityValidationMessage.CITIES_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(cityDTOList);
        } else {
            log.info("CityServiceImpl -> getByIdCountryProvinceAndDistrict() => Cities not found.");
            throw new RecordNotFoundException(CityValidationMessage.CITIES_NOT_FOUND);
        }
        log.info("CityServiceImpl -> getByIdCountryProvinceAndDistrict() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

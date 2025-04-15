package com.pdev.rempms.locationservice.service.impl.locationInformation;

import com.pdev.rempms.locationservice.enums.CommonStatus;
import com.pdev.rempms.locationservice.constants.validation.CommonValidationMessage;
import com.pdev.rempms.locationservice.constants.validation.LocationInformationValidationMessage;
import com.pdev.rempms.locationservice.dto.locationInformation.LocationInformationDTO;
import com.pdev.rempms.locationservice.exception.RecordNotFoundException;
import com.pdev.rempms.locationservice.mapper.location.LocationInformationMapper;
import com.pdev.rempms.locationservice.model.*;
import com.pdev.rempms.locationservice.repository.*;
import com.pdev.rempms.locationservice.service.locationInformation.LocationInformationService;
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
public class LocationInformationServiceImpl implements LocationInformationService {

    private final CommonUtil commonUtil;
    private final LocationInformationRepository locationInformationRepository;
    private final LocationInformationMapper locationInformationMapper;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final CountryRepository countryRepository;

    /**
     * save or update location information
     *
     * @param dto - location information saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> saveUpdateLocationInformation(LocationInformationDTO dto) {
        log.info("LocationInformationServiceImpl -> saveUpdateLocationInformation() => started!");
        CommonResponse commonResponse = new CommonResponse();
        LocationInformation locationInformation = new LocationInformation();
        String validation = validateLocationInfo(dto);
        if (validation != null) {
            log.info("LocationInformationServiceImpl -> saveUpdateLocationInformation() => Validation available!");
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(validation);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        City city = cityRepository.findCityByIdAndCommonStatus(Long.valueOf(dto.getIdCountry()), CommonStatus.ACTIVE.getValue());
        Country country = countryRepository.findCountryByIdAndCommonStatus(Long.valueOf(dto.getIdCountry()), CommonStatus.ACTIVE.getValue());
        Province province = provinceRepository.findProvinceByIdAndCommonStatus(Long.valueOf(dto.getIdProvince()), CommonStatus.ACTIVE.getValue());
        District district = districtRepository.findDistrictByIdAndCommonStatus(Long.valueOf(dto.getIdDistrict()), CommonStatus.ACTIVE.getValue());
        if (!CommonValidation.stringNullValidation(dto.getIdLocationInformation())) {
            log.info("LocationInformationServiceImpl -> saveUpdateLocationInformation() => Update existing LocationInfo!");
            locationInformation = locationInformationRepository.findById(Long.valueOf(dto.getIdLocationInformation())).get();
            locationInformation = locationInformationMapper.toEntity(locationInformation, dto, city, district, province, country);
            locationInformation.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            locationInformation.getAuditData().setUpdatedBy(commonUtil.getUsername()); //need further development for authorization
        } else {
            log.info("LocationInformationServiceImpl -> saveUpdateLocationInformation() => Save new LocationInfo!");
            AuditData auditData = new AuditData(LocalDateTime.now(), commonUtil.getUsername());
            locationInformation.setAuditData(auditData);
            locationInformation = locationInformationMapper.toEntity(locationInformation, dto, city, district, province, country);
        }
        LocationInformation createdLocationInfo = locationInformationRepository.save(locationInformation);
        LocationInformationDTO createdLocation = locationInformationMapper.toDto(new LocationInformationDTO(), createdLocationInfo);

        commonResponse.setData(createdLocation);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(LocationInformationValidationMessage.LOCATION_INFO_SAVED_SUCCESS);
        log.info("LocationInformationServiceImpl -> saveUpdateLocationInformation() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Validate location info save data
     *
     * @param dto - location info updating details
     * @return - Validation messages
     * @author maleeshasa
     */
    private String validateLocationInfo(LocationInformationDTO dto) {
        log.info("LocationInformationServiceImpl -> validateLocationInfo() => started!");
        String validation = null;
        if (CommonValidation.stringNullValidation(dto.getNo())) {
            validation = LocationInformationValidationMessage.NO_EMPTY;
        } else if (CommonValidation.stringNullValidation(dto.getStreetNo1())) {
            validation = LocationInformationValidationMessage.STREET_01_EMPTY;
        } else if (CommonValidation.stringNullValidation(dto.getStreetNo2())) {
            validation = LocationInformationValidationMessage.STREET_02_EMPTY;
        } else if (dto.getIdCountry().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COUNTRY_NOT_SELECT;
        } else if (dto.getIdProvince().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.PROVINCE_NOT_SELECT;
        } else if (dto.getIdDistrict().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.DISTRICT_NOT_SELECT;
        } else if (dto.getIdCity().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.CITY_NOT_SELECT;
        }
        log.info("LocationInformationServiceImpl -> validateLocationInfo() => ended!");
        return validation;
    }

    /**
     * Get active location information by id
     *
     * @param idLocationInformation - location information id
     * @return - Active district data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getActiveLocationInformationById(Long idLocationInformation) {
        log.info("LocationInformationServiceImpl -> getActiveLocationInformationById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        Optional<LocationInformation> locationInformation = locationInformationRepository.findById(idLocationInformation);
        if (locationInformation.isPresent()) {
            log.info("CityServiceImpl -> getActiveLocationInformationById() => Location Information found!");
            LocationInformationDTO dto = new LocationInformationDTO();
            dto = locationInformationMapper.toDto(dto, locationInformation.get());
            commonResponse.setMessage(LocationInformationValidationMessage.LOCATION_INFO_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("CityServiceImpl -> getActiveLocationInformationById() => Location Information not found!");
            throw new RecordNotFoundException(LocationInformationValidationMessage.LOCATION_INFO_NOT_FOUND);
        }
        log.info("LocationInformationServiceImpl -> getActiveLocationInformationById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all active location infos
     *
     * @return - All Active location info data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActiveLocationInformation() {
        log.info("LocationInformationServiceImpl -> getAllActiveLocationInformation() => started.");
        CommonResponse commonResponse = new CommonResponse();
        List<LocationInformation> locationInformationList = locationInformationRepository.findAll();
        if (!locationInformationList.isEmpty()) {
            log.info("LocationInformationServiceImpl -> getAllActiveLocationInformation() => Locations found.");
            List<LocationInformationDTO> locationInformationDTOList = locationInformationList.stream()
                    .map(locationInformation -> {
                        LocationInformationDTO dto = new LocationInformationDTO();
                        return locationInformationMapper.toDto(dto, locationInformation);
                    })
                    .toList();
            commonResponse.setMessage(LocationInformationValidationMessage.LOCATION_INFOs_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(locationInformationDTOList);
        } else {
            log.info("LocationInformationServiceImpl -> getAllActiveLocationInformation() => Locations not found.");
            throw new RecordNotFoundException(LocationInformationValidationMessage.LOCATION_INFO_NOT_FOUND);
        }
        log.info("LocationInformationServiceImpl -> getAllActiveLocationInformation() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete location info by location info id
     *
     * @param idLocationInformation - idLocationInformation id
     * @return - idLocationInformation deleted success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> deleteLocationInformationById(Long idLocationInformation) {
        log.info("deleteLocationInformationById -> deleteLocationInformationById() => started.");
        CommonResponse commonResponse = new CommonResponse();
        Optional<LocationInformation> locationInformation = locationInformationRepository.findById(idLocationInformation);
        if (locationInformation.isPresent()) {
            log.info("deleteLocationInformationById -> deleteLocationInformationById() => Location Information found.");
            locationInformation.get().setCommonStatus(CommonStatus.DELETED.getValue());
            locationInformation.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            locationInformation.get().getAuditData().setUpdatedBy(commonUtil.getUsername()); //need further development for authorization
            locationInformationRepository.save(locationInformation.get());
            commonResponse.setMessage(LocationInformationValidationMessage.LOCATION_INFO_DELETED_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("deleteLocationInformationById -> deleteLocationInformationById() => Location Information not found.");
            throw new RecordNotFoundException(LocationInformationValidationMessage.LOCATION_INFO_NOT_FOUND);
        }
        log.info("deleteLocationInformationById -> deleteLocationInformationById() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

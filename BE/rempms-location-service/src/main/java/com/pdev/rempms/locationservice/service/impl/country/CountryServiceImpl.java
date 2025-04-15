package com.pdev.rempms.locationservice.service.impl.country;

import com.pdev.rempms.locationservice.enums.CommonStatus;
import com.pdev.rempms.locationservice.constants.validation.CommonValidationMessage;
import com.pdev.rempms.locationservice.constants.validation.CountryValidationMessage;
import com.pdev.rempms.locationservice.dto.country.CountryDTO;
import com.pdev.rempms.locationservice.exception.RecordNotFoundException;
import com.pdev.rempms.locationservice.mapper.country.CountryMapper;
import com.pdev.rempms.locationservice.model.AuditData;
import com.pdev.rempms.locationservice.model.Country;
import com.pdev.rempms.locationservice.repository.CountryRepository;
import com.pdev.rempms.locationservice.service.country.CountryService;
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
public class CountryServiceImpl implements CountryService {

    private final CommonUtil commonUtil;
    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;

    /**
     * save or update country
     *
     * @param dto - country saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> saveUpdateCountry(CountryDTO dto) {
        log.info("CountryServiceImpl -> saveUpdateCountry() => started!");
        CommonResponse commonResponse = new CommonResponse();
        Country country = new Country();
        String validation = validateCountry(dto);
        if (validation != null) {
            log.info("CountryServiceImpl -> saveUpdateCountry() => Validation available!");
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(validation);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        if (!CommonValidation.stringNullValidation(dto.getIdCountry())) {
            log.info("CountryServiceImpl -> saveUpdateCountry() => Update existing country!");
            country = countryRepository.findById(Long.valueOf(dto.getIdCountry())).get();
            country = countryMapper.toEntity(country, dto);
            country.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            country.getAuditData().setUpdatedBy(commonUtil.getUsername()); //need further development for authorization
        } else {
            log.info("CountryServiceImpl -> saveUpdateCountry() => Save new country!");
            AuditData auditData = new AuditData(LocalDateTime.now(), commonUtil.getUsername());
            country = countryMapper.toEntity(country, dto);
            country.setAuditData(auditData);
        }
        country.setCommonStatus(CommonStatus.ACTIVE.getValue());
        countryRepository.save(country);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(CountryValidationMessage.COUNTRY_SAVED_SUCCESS);
        log.info("CountryServiceImpl -> saveUpdateCountry() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Validate country save data
     *
     * @param dto - country saving or updating details
     * @return - Validation messages
     * @author maleeshasa
     */
    private String validateCountry(CountryDTO dto) {
        log.info("CountryServiceImpl -> validateCountry() => started!");
        String validation = null;
        if (CommonValidation.stringNullValidation(dto.getCountryName())) {
            validation = CommonValidationMessage.NAME_EMPTY;
        } else if (dto.getCommonStatus().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COMMON_STATUS_EMPTY;
        } else if (CommonValidation.stringNullValidation(dto.getCountryCode())) {
            validation = CountryValidationMessage.COUNTRY_CODE_EMPTY;
        } else if (CommonValidation.stringNullValidation(dto.getNationality())) {
            validation = CountryValidationMessage.COUNTRY_NATIONALITY_EMPTY;
        }
        log.info("CountryServiceImpl -> validateCountry() => ended!");
        return validation;
    }

    /**
     * This method is allowed to get country by id
     *
     * @param idCountry {@link Long} - country id
     * @return {@link ResponseEntity<CommonResponse>} - country response
     * @author @Maleesha99
     */
    @Override
    public ResponseEntity<CommonResponse> getActiveCountryById(Long idCountry) {
        log.info("CountryServiceImpl -> getActiveCountryById() => started.");
        CommonResponse commonResponse = new CommonResponse();
        Country country = countryRepository.findCountryByIdAndCommonStatus(idCountry, CommonStatus.ACTIVE.getValue());
        if (country != null) {
            log.info("CountryServiceImpl -> getActiveCountryById() => Country found");
            CountryDTO dto = new CountryDTO();
            dto = countryMapper.toDto(dto, country);
            commonResponse.setMessage(CountryValidationMessage.COUNTRY_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("CountryServiceImpl -> getActiveCountryById() => Country not found");
            throw new RecordNotFoundException(CountryValidationMessage.COUNTRY_NOT_FOUND);
        }
        log.info("CountryServiceImpl -> getActiveCountryById() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all active countries
     *
     * @return - All Active countries data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActiveCountries() {
        log.info("CountryServiceImpl -> getAllActiveCountries() => started.");
        CommonResponse commonResponse = new CommonResponse();
        List<Country> countries = countryRepository.findAll();
        if (!countries.isEmpty()) {
            log.info("CountryServiceImpl -> getAllActiveCountries() => Countries found!");
            List<CountryDTO> countryDTOList = countries.stream()
                    .filter(country -> country.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                    .map(country -> {
                        CountryDTO dto = new CountryDTO();
                        return countryMapper.toDto(dto, country);
                    })
                    .toList();
            commonResponse.setMessage(CountryValidationMessage.COUNTRIES_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(countryDTOList);
        } else {
            log.info("CountryServiceImpl -> getAllActiveCountries() => Countries not found!");
            throw new RecordNotFoundException(CountryValidationMessage.COUNTRY_NOT_FOUND);
        }
        log.info("CountryServiceImpl -> getAllActiveCountries() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete country by country id
     *
     * @param idCountry - country id
     * @return - Country deleted success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> deleteCountryById(Long idCountry) {
        log.info("CountryServiceImpl -> deleteCountryById() => started.");
        CommonResponse commonResponse = new CommonResponse();
        Optional<Country> country = countryRepository.findById(idCountry);
        if (country.isPresent()) {
            log.info("CountryServiceImpl -> deleteCountryById() => Country found.");
            country.get().setCommonStatus(CommonStatus.DELETED.getValue());
            country.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            country.get().getAuditData().setUpdatedBy(commonUtil.getUsername());
            countryRepository.save(country.get());
            commonResponse.setMessage(CountryValidationMessage.COUNTRY_DELETED_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("CountryServiceImpl -> deleteCountryById() => Country not found.");
            throw new RecordNotFoundException(CountryValidationMessage.COUNTRY_NOT_FOUND);
        }
        log.info("CountryServiceImpl -> deleteCountryById() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

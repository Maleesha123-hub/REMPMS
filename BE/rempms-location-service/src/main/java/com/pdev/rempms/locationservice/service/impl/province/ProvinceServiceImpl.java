package com.pdev.rempms.locationservice.service.impl.province;

import com.pdev.rempms.locationservice.enums.CommonStatus;
import com.pdev.rempms.locationservice.constants.validation.CommonValidationMessage;
import com.pdev.rempms.locationservice.constants.validation.ProvinceValidationMessage;
import com.pdev.rempms.locationservice.dto.province.ProvinceDTO;
import com.pdev.rempms.locationservice.exception.RecordNotFoundException;
import com.pdev.rempms.locationservice.mapper.province.ProvinceMapper;
import com.pdev.rempms.locationservice.model.AuditData;
import com.pdev.rempms.locationservice.model.Country;
import com.pdev.rempms.locationservice.model.Province;
import com.pdev.rempms.locationservice.repository.CountryRepository;
import com.pdev.rempms.locationservice.repository.ProvinceRepository;
import com.pdev.rempms.locationservice.service.province.ProvinceService;
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
public class ProvinceServiceImpl implements ProvinceService {

    private final CommonUtil commonUtil;
    private final CountryRepository countryRepository;
    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;

    /**
     * save or update province
     *
     * @param dto - province saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> saveUpdateProvince(ProvinceDTO dto) {
        log.info("ProvinceServiceImpl -> saveUpdateProvince() => stared");
        CommonResponse commonResponse = new CommonResponse();
        Province province = new Province();
        String validation = validateProvince(dto);
        if (validation != null) {
            log.info("ProvinceServiceImpl -> saveUpdateProvince() => Validation available!");
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(validation);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        Country country = countryRepository.findById(Long.valueOf(dto.getIdCountry())).get();
        if (!CommonValidation.stringNullValidation(dto.getIdProvince())) {
            log.info("ProvinceServiceImpl -> saveUpdateProvince() => Update existing province!");
            province = provinceRepository.findById(Long.valueOf(dto.getIdProvince())).get();
            province = provinceMapper.toEntity(province, dto, country);
            province.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            province.getAuditData().setUpdatedBy(commonUtil.getUsername());
        } else {
            log.info("ProvinceServiceImpl -> saveUpdateProvince() => Save new province!");
            AuditData auditData = new AuditData(LocalDateTime.now(), commonUtil.getUsername());
            province.setAuditData(auditData);
            province = provinceMapper.toEntity(province, dto, country);
        }
        provinceRepository.save(province);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(ProvinceValidationMessage.PROVINCE_SAVED_SUCCESS);
        log.info("ProvinceServiceImpl -> saveUpdateProvince() => ended");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Validate province save data
     *
     * @param dto - province saving or updating details
     * @return - Validation messages
     * @author maleeshasa
     */
    private String validateProvince(ProvinceDTO dto) {
        log.info("ProvinceServiceImpl -> validateProvince() => started!");
        String validation = null;
        if (CommonValidation.stringNullValidation(dto.getProvinceName())) {
            validation = CommonValidationMessage.NAME_EMPTY;
        } else if (dto.getIdCountry().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COUNTRY_NOT_SELECT;
        } else if (dto.getCommonStatus().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COMMON_STATUS_EMPTY;
        }
        log.info("ProvinceServiceImpl -> validateProvince() => ended!");
        return validation;
    }

    /**
     * Get active province by id
     *
     * @param idProvince - province id
     * @return - Active province data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getActiveProvinceById(Long idProvince) {
        log.info("ProvinceServiceImpl -> getActiveProvinceById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        Province province = provinceRepository.findProvinceByIdAndCommonStatus(idProvince, CommonStatus.ACTIVE.getValue());
        if (province != null) {
            log.info("ProvinceServiceImpl -> getActiveProvinceById() => Province found!");
            ProvinceDTO dto = new ProvinceDTO();
            dto = provinceMapper.toDto(dto, province);
            commonResponse.setMessage(ProvinceValidationMessage.PROVINCE_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("ProvinceServiceImpl -> getActiveProvinceById() => Province not found!");
            throw new RecordNotFoundException(ProvinceValidationMessage.PROVINCE_NOT_FOUND);
        }
        log.info("ProvinceServiceImpl -> getActiveProvinceById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all active provinces
     *
     * @return - All Active provinces data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActiveProvinces() {
        log.info("ProvinceServiceImpl -> getAllActiveProvince() => started!");
        CommonResponse commonResponse = new CommonResponse();
        List<Province> provinceList = provinceRepository.findAll();
        if (!provinceList.isEmpty()) {
            log.info("ProvinceServiceImpl -> getAllActiveProvince() => Provinces found!");
            List<ProvinceDTO> provinceDTOList = provinceList.stream()
                    .filter(province -> province.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                    .map(province -> {
                        ProvinceDTO dto = new ProvinceDTO();
                        return provinceMapper.toDto(dto, province);
                    })
                    .toList();
            commonResponse.setMessage(ProvinceValidationMessage.PROVINCES_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(provinceDTOList);
        } else {
            log.info("ProvinceServiceImpl -> getAllActiveProvince() => Provinces not found!");
            throw new RecordNotFoundException(ProvinceValidationMessage.PROVINCE_NOT_FOUND);
        }
        log.info("ProvinceServiceImpl -> getAllActiveProvince() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete province by province id
     *
     * @param idProvince - province id
     * @return - Province deleted success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> deleteProvinceById(Long idProvince) {
        log.info("ProvinceServiceImpl -> deleteProvinceById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        Optional<Province> province = provinceRepository.findById(idProvince);
        if (province.isPresent()) {
            log.info("ProvinceServiceImpl -> deleteProvinceById() => Province found!");
            province.get().setCommonStatus(CommonStatus.DELETED.getValue());
            province.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            province.get().getAuditData().setUpdatedBy(commonUtil.getUsername());
            provinceRepository.save(province.get());
            commonResponse.setMessage(ProvinceValidationMessage.PROVINCE_DELETED_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("ProvinceServiceImpl -> deleteProvinceById() => Province not found!");
            throw new RecordNotFoundException(ProvinceValidationMessage.PROVINCE_NOT_FOUND);
        }
        log.info("ProvinceServiceImpl -> deleteProvinceById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get provinces by country id
     *
     * @param idCountry - country id
     * @return - Provinces.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getProvincesByCountryId(Long idCountry) {
        log.info("ProvinceServiceImpl -> getProvincesByCountryId() => started!");
        CommonResponse commonResponse = new CommonResponse();
        List<ProvinceDTO> provinceDTOList;
        List<Province> provinceList = provinceRepository.findProvinceByCountryIdAndCommonStatus(idCountry,
                CommonStatus.ACTIVE.getValue());
        if (!provinceList.isEmpty()) {
            log.info("ProvinceServiceImpl -> getProvincesByCountryId() => Provinces found!");
            provinceDTOList = provinceList.stream().map(province -> {
                ProvinceDTO dto = new ProvinceDTO();
                return provinceMapper.toDto(dto, province);
            }).toList();
            commonResponse.setData(provinceDTOList);
            commonResponse.setMessage(ProvinceValidationMessage.PROVINCES_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("ProvinceServiceImpl -> getProvincesByCountryId() => Provinces not found!");
            throw new RecordNotFoundException(ProvinceValidationMessage.PROVINCES_NOT_FOUND);
        }
        log.info("ProvinceServiceImpl -> getProvincesByCountryId() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

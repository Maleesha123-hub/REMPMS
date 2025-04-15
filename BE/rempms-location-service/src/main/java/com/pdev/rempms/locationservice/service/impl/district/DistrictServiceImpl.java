package com.pdev.rempms.locationservice.service.impl.district;

import com.pdev.rempms.locationservice.enums.CommonStatus;
import com.pdev.rempms.locationservice.constants.validation.CommonValidationMessage;
import com.pdev.rempms.locationservice.constants.validation.DistrictValidationMessage;
import com.pdev.rempms.locationservice.dto.district.DistrictDTO;
import com.pdev.rempms.locationservice.exception.RecordNotFoundException;
import com.pdev.rempms.locationservice.mapper.district.DistrictMapper;
import com.pdev.rempms.locationservice.model.AuditData;
import com.pdev.rempms.locationservice.model.Country;
import com.pdev.rempms.locationservice.model.District;
import com.pdev.rempms.locationservice.model.Province;
import com.pdev.rempms.locationservice.repository.CountryRepository;
import com.pdev.rempms.locationservice.repository.DistrictRepository;
import com.pdev.rempms.locationservice.repository.ProvinceRepository;
import com.pdev.rempms.locationservice.service.district.DistrictService;
import com.pdev.rempms.locationservice.util.CommonResponse;
import com.pdev.rempms.locationservice.util.CommonUtil;
import com.pdev.rempms.locationservice.util.CommonValidation;
import com.pdev.rempms.locationservice.util.DateTimeUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DistrictServiceImpl implements DistrictService {

    private final CommonUtil commonUtil;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;
    private final CountryRepository countryRepository;

    /**
     * save or update district
     *
     * @param dto - district saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> saveUpdateDistrict(DistrictDTO dto) {
        log.info("DistrictServiceImpl -> saveUpdateDistrict() => stared");
        CommonResponse commonResponse = new CommonResponse();
        District district = new District();
        Province province;
        Country country;
        String validation = validateDistrict(dto);
        if (validation != null) {
            log.info("DistrictServiceImpl -> saveUpdateDistrict() => Validation available!");
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(validation);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        province = provinceRepository.findById(Long.valueOf(dto.getIdProvince())).get();
        country = countryRepository.findById(Long.valueOf(dto.getIdCountry())).get();
        if (!CommonValidation.stringNullValidation(dto.getIdDistrict())) {
            log.info("DistrictServiceImpl -> saveUpdateDistrict() => Update existing district!");
            district = districtRepository.findById(Long.valueOf(dto.getIdDistrict())).get();
            district = districtMapper.toEntity(district, dto, province, country);
            district.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            district.getAuditData().setUpdatedBy(commonUtil.getUsername()); //need further development for authorization
        } else {
            log.info("DistrictServiceImpl -> saveUpdateDistrict() => Save new district!");
            AuditData auditData = new AuditData(LocalDateTime.now(), commonUtil.getUsername());
            district = districtMapper.toEntity(district, dto, province, country);
            district.setAuditData(auditData);
        }
        districtRepository.save(district);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(DistrictValidationMessage.DISTRICT_SAVED_SUCCESS);
        log.info("DistrictServiceImpl -> saveUpdateDistrict() => ended");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Validate district save data
     *
     * @param dto - district saving or updating details
     * @return - Validation messages
     * @author maleeshasa
     */
    private String validateDistrict(DistrictDTO dto) {
        log.info("DistrictServiceImpl -> validateDistrict() => started!");
        String validation = null;
        if (CommonValidation.stringNullValidation(dto.getDistrictName())) {
            validation = CommonValidationMessage.NAME_EMPTY;
        } else if (dto.getIdCountry().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COUNTRY_NOT_SELECT;
        } else if (dto.getIdProvince().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.PROVINCE_NOT_SELECT;
        } else if (dto.getCommonStatus().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COMMON_STATUS_EMPTY;
        }
        log.info("DistrictServiceImpl -> validateDistrict() => ended!");
        return validation;
    }

    /**
     * Get active district by id
     *
     * @param idDistrict - district id
     * @return - Active district data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getActiveDistrictById(Long idDistrict) {
        log.info("DistrictServiceImpl -> getActiveDistrictById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        District district = districtRepository.findDistrictByIdAndCommonStatus(idDistrict, CommonStatus.ACTIVE.getValue());
        if (district != null) {
            log.info("DistrictServiceImpl -> getActiveDistrictById() => District found!");
            DistrictDTO dto = new DistrictDTO();
            dto = districtMapper.toDto(dto, district);
            commonResponse.setMessage(DistrictValidationMessage.DISTRICT_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("DistrictServiceImpl -> getActiveDistrictById() => District not found!");
            throw new RecordNotFoundException(DistrictValidationMessage.DISTRICT_NOT_FOUND);
        }
        log.info("DistrictServiceImpl -> getActiveDistrictById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all active districts
     *
     * @return - All Active districts data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActiveDistricts() {
        log.info("DistrictServiceImpl -> getAllActiveDistricts() => started!");
        CommonResponse commonResponse = new CommonResponse();
        List<District> districts = districtRepository.findAll();
        if (!districts.isEmpty()) {
            log.info("ProvinceServiceImpl -> getAllActiveProvince() => Districts found!");
            List<DistrictDTO> districtDTOList = districts.stream()
                    .filter(district -> district.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                    .map(district -> {
                        DistrictDTO dto = new DistrictDTO();
                        return districtMapper.toDto(dto, district);
                    })
                    .toList();
            commonResponse.setMessage(DistrictValidationMessage.DISTRICTS_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(districtDTOList);
        } else {
            log.info("ProvinceServiceImpl -> getAllActiveProvince() => Districts not found!");
            throw new RecordNotFoundException(DistrictValidationMessage.DISTRICT_NOT_FOUND);
        }
        log.info("DistrictServiceImpl -> getAllActiveDistricts() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete district by district id
     *
     * @param idDistrict - district id
     * @return - District deleted success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> deleteDistrictById(Long idDistrict) {
        log.info("DistrictServiceImpl -> deleteDistrictById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        Optional<District> district = districtRepository.findById(idDistrict);
        if (district.isPresent()) {
            log.info("DistrictServiceImpl -> deleteDistrictById() => District found!");
            district.get().setCommonStatus(CommonStatus.DELETED.getValue());
            district.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            district.get().getAuditData().setUpdatedBy(commonUtil.getUsername());
            districtRepository.save(district.get());
            commonResponse.setMessage(DistrictValidationMessage.DISTRICT_DELETED_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("DistrictServiceImpl -> deleteDistrictById() => District not found!");
            throw new RecordNotFoundException(DistrictValidationMessage.DISTRICT_NOT_FOUND);
        }
        log.info("DistrictServiceImpl -> deleteDistrictById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get districts by country id and province id
     *
     * @param idCountry - country id
     * @param idProvince - province id
     * @return - districts success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getDistrictsByCountryIdAndProvinceId(Long idProvince, Long idCountry) {
        log.info("DistrictServiceImpl -> getDistrictsByCountryIdAndProvinceId() => started!");
        CommonResponse commonResponse = new CommonResponse();
        List<DistrictDTO> districtDTOList;
        List<District> districtList = districtRepository.findDistrictByProvinceIdAndCountryId(idProvince, idCountry);
        if (!districtList.isEmpty()) {
            log.info("DistrictServiceImpl -> getDistrictsByCountryIdAndProvinceId() => Districts found!");
            districtDTOList = districtList.stream().map(district -> {
                DistrictDTO dto = new DistrictDTO();
                return districtMapper.toDto(dto, district);
            }).toList();
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage(DistrictValidationMessage.DISTRICTS_FOUND);
            commonResponse.setData(districtDTOList);
        } else {
            log.info("DistrictServiceImpl -> getDistrictsByCountryIdAndProvinceId() => Districts not found!");
            throw new RecordNotFoundException(DistrictValidationMessage.DISTRICTS_NOT_FOUND);
        }
        log.info("DistrictServiceImpl -> getDistrictsByCountryIdAndProvinceId() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

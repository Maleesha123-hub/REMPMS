package com.pdev.rempms.communicationservice.service.impl;

import com.pdev.rempms.communicationservice.constants.enums.CommonStatus;
import com.pdev.rempms.communicationservice.constants.validation.CommonValidationMessage;
import com.pdev.rempms.communicationservice.constants.validation.PreferredCommunicationValidationMessage;
import com.pdev.rempms.communicationservice.dto.language.LanguageDTO;
import com.pdev.rempms.communicationservice.dto.preferredCommunication.PreferredCommunicationDTO;
import com.pdev.rempms.communicationservice.exception.RecordNotFoundException;
import com.pdev.rempms.communicationservice.mapper.PreferredCommunicationMapper;
import com.pdev.rempms.communicationservice.model.AuditData;
import com.pdev.rempms.communicationservice.model.PreferredCommunication;
import com.pdev.rempms.communicationservice.repository.PreferredCommunicationRepository;
import com.pdev.rempms.communicationservice.service.PreferredCommunicationService;
import com.pdev.rempms.communicationservice.util.CommonResponse;
import com.pdev.rempms.communicationservice.util.CommonValidation;
import com.pdev.rempms.communicationservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author maleeshasa
 * @Date 23/11/2023
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PreferredCommunicationImpl implements PreferredCommunicationService {

    private final PreferredCommunicationRepository preferredCommunicationRepository;
    private final PreferredCommunicationMapper preferredCommunicationMapper;

    /**
     * save or update preferred communication
     *
     * @param dto - Preferred communication saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> saveUpdatePreferredCommunication(PreferredCommunicationDTO dto) {
        log.info("PreferredCommunicationImpl -> saveUpdatePreferredCommunication() => started!");
        CommonResponse commonResponse = new CommonResponse();
        PreferredCommunication preferredCommunication = new PreferredCommunication();
        String validation = validateLanguage(dto);
        if (validation != null) {
            log.info("LanguageServiceImpl -> saveUpdateLanguage() => Validation available!");
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(validation);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        if (!CommonValidation.stringNullValidation(dto.getIdPreferredCommunication())) {
            log.info("PreferredCommunicationImpl -> saveUpdatePreferredCommunication() => Update existing preferred comm.!");
            preferredCommunication = preferredCommunicationRepository.findById(Long.valueOf(dto.getIdPreferredCommunication())).get();
            preferredCommunication = preferredCommunicationMapper.toEntity(preferredCommunication, dto);
            preferredCommunication.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            preferredCommunication.getAuditData().setUpdatedBy(Long.valueOf(1)); //need further development for authorization
        } else {
            log.info("PreferredCommunicationImpl -> saveUpdatePreferredCommunication() => Save new preferred comm.!");
            AuditData auditData = new AuditData();
            auditData.setCreatedOn(DateTimeUtil.getSriLankaTime());
            auditData.setCreatedBy(Long.valueOf(1));
            preferredCommunication = preferredCommunicationMapper.toEntity(preferredCommunication, dto);
            preferredCommunication.setAuditData(auditData);
        }
        preferredCommunicationRepository.save(preferredCommunication);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(PreferredCommunicationValidationMessage.PREFERRED_COMM_SAVED_SUCCESS);
        log.info("PreferredCommunicationImpl -> saveUpdatePreferredCommunication() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Validate preferred communication save data
     *
     * @param dto - Preferred communication saving or updating details
     * @return - Validation messages
     * @author maleeshasa
     */
    private String validateLanguage(PreferredCommunicationDTO dto) {
        log.info("LanguageServiceImpl -> validateLanguage() => started!");
        String validation = null;
        if (CommonValidation.stringNullValidation(dto.getPreferredCommunicationMethod())) {
            validation = CommonValidationMessage.NAME_EMPTY;
        } else if (CommonValidation.stringNullValidation(dto.getPreferredCommunicationDescription())) {
            validation = CommonValidationMessage.DESCRIPTION_EMPTY;
        } else if (dto.getPreferredCommunicationCommonStatus().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COMMON_STATUS_EMPTY;
        }
        log.info("LanguageServiceImpl -> validateLanguage() => ended!");
        return validation;
    }

    /**
     * Get active preferred communication by id
     *
     * @param id - preferred communication id
     * @return - Active preferred communication data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getActivePreferredCommunicationById(Long id) {
        log.info("PreferredCommunicationImpl -> getActivePreferredCommunicationById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        PreferredCommunication preferredCommunication = preferredCommunicationRepository.getPreferredCommunicationByIdAndCommonStatus(id, CommonStatus.ACTIVE.getValue());
        if (preferredCommunication != null) {
            log.info("PreferredCommunicationImpl -> getActivePreferredCommunicationById() => Preferred comm. found!");
            PreferredCommunicationDTO dto = new PreferredCommunicationDTO();
            dto = preferredCommunicationMapper.toDto(dto, preferredCommunication);
            commonResponse.setMessage(PreferredCommunicationValidationMessage.PREFERRED_COMM_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("PreferredCommunicationImpl -> getActivePreferredCommunicationById() => Preferred comm. not found!");
            throw new RecordNotFoundException(PreferredCommunicationValidationMessage.PREFERRED_COMM_NOT_FOUND);
        }
        log.info("PreferredCommunicationImpl -> getActivePreferredCommunicationById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all active preferred communications
     *
     * @return - All Active preferred communications data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActivePreferredCommunications() {
        log.info("PreferredCommunicationImpl -> getAllActivePreferredCommunications() => started!");
        CommonResponse commonResponse = new CommonResponse();
        List<PreferredCommunication> preferredCommunications = preferredCommunicationRepository.findAll();
        if (!preferredCommunications.isEmpty()) {
            log.info("PreferredCommunicationImpl -> getAllActivePreferredCommunications() => Preferred comms. found!");
            List<PreferredCommunicationDTO> preferredCommunicationDTOList = preferredCommunications.stream()
                    .filter(preferredCommunication -> preferredCommunication.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                    .map(preferredCommunication -> {
                        PreferredCommunicationDTO dto = new PreferredCommunicationDTO();
                        return preferredCommunicationMapper.toDto(dto, preferredCommunication);
                    })
                    .toList();
            commonResponse.setMessage(PreferredCommunicationValidationMessage.PREFERRED_COMM_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(preferredCommunicationDTOList);
        } else {
            log.info("PreferredCommunicationImpl -> getAllActivePreferredCommunications() => Preferred comms. not found!");
            throw new RecordNotFoundException(PreferredCommunicationValidationMessage.PREFERRED_COMM_NOT_FOUND);
        }
        log.info("PreferredCommunicationImpl -> getAllActivePreferredCommunications() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete preferred communication by id
     *
     * @param id - preferred communication id
     * @return - preferred communication deleted success info.
     * @author maleeshasa
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deletePreferredCommunicationById(Long id) {
        log.info("PreferredCommunicationImpl -> deletePreferredCommunicationById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        Optional<PreferredCommunication> preferredCommunication = preferredCommunicationRepository.findById(id);
        if (preferredCommunication.isPresent()) {
            log.info("PreferredCommunicationImpl -> deletePreferredCommunicationById() => Preferred comm. found!");
            preferredCommunication.get().setCommonStatus(CommonStatus.DELETED.getValue());
            preferredCommunication.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            preferredCommunication.get().getAuditData().setUpdatedBy(Long.valueOf(1));
            preferredCommunicationRepository.save(preferredCommunication.get());
            commonResponse.setMessage(PreferredCommunicationValidationMessage.PREFERRED_COMM_DELETED_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("PreferredCommunicationImpl -> deletePreferredCommunicationById() => Preferred comm. not found!");
            throw new RecordNotFoundException(PreferredCommunicationValidationMessage.PREFERRED_COMM_NOT_FOUND);
        }
        log.info("PreferredCommunicationImpl -> deletePreferredCommunicationById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

package com.pdev.rempms.communicationservice.service.impl;

import com.pdev.rempms.communicationservice.constants.enums.CommonStatus;
import com.pdev.rempms.communicationservice.constants.validation.CommunicationInformationValidationMessage;
import com.pdev.rempms.communicationservice.dto.communicationInformation.CommunicationInformationDTO;
import com.pdev.rempms.communicationservice.exception.RecordNotFoundException;
import com.pdev.rempms.communicationservice.mapper.CommunicationInformationMapper;
import com.pdev.rempms.communicationservice.model.AuditData;
import com.pdev.rempms.communicationservice.model.CommunicationInformation;
import com.pdev.rempms.communicationservice.model.Language;
import com.pdev.rempms.communicationservice.model.PreferredCommunication;
import com.pdev.rempms.communicationservice.repository.CommunicationInformationRepository;
import com.pdev.rempms.communicationservice.repository.LanguageRepository;
import com.pdev.rempms.communicationservice.repository.PreferredCommunicationRepository;
import com.pdev.rempms.communicationservice.service.CommunicationInformationService;
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
public class CommunicationInformationImpl implements CommunicationInformationService {

    private final CommunicationInformationRepository communicationInformationRepository;
    private final CommunicationInformationMapper communicationInformationMapper;
    private final LanguageRepository languageRepository;
    private final PreferredCommunicationRepository preferredCommunicationRepository;

    /**
     * save or update communication information
     *
     * @param dto - Communication information saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> saveUpdateCommunicationInformation(CommunicationInformationDTO dto) {
        log.info("CommunicationInformationImpl -> saveUpdateCommunicationInformation() => started!");
        CommonResponse commonResponse = new CommonResponse();
        CommunicationInformation communicationInformation = new CommunicationInformation();
        Language language = languageRepository.findById(Long.valueOf(dto.getIdLanguage())).get();
        PreferredCommunication preferredCommunication = preferredCommunicationRepository.findById(Long.valueOf(dto.getIdPreferredCommunication())).get();
        if (!CommonValidation.stringNullValidation(dto.getIdCommunicationInformation())) {
            log.info("CommunicationInformationImpl -> saveUpdateCommunicationInformation() => Update existing communication information!");
            communicationInformation = communicationInformationRepository.findById(Long.valueOf(dto.getIdCommunicationInformation())).get();
            communicationInformation = communicationInformationMapper.toEntity(communicationInformation, dto, language, preferredCommunication);
            communicationInformation.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            communicationInformation.getAuditData().setUpdatedBy(Long.valueOf(1)); //need further development for authorization
        } else {
            log.info("CommunicationInformationImpl -> saveUpdateCommunicationInformation() => Save new communication information!");
            AuditData auditData = new AuditData();
            communicationInformation = communicationInformationMapper.toEntity(communicationInformation, dto, language, preferredCommunication);
            auditData.setCreatedOn(DateTimeUtil.getSriLankaTime());
            auditData.setCreatedBy(Long.valueOf(1)); //need further development for authorization
            communicationInformation.setAuditData(auditData);
        }
        communicationInformationRepository.save(communicationInformation);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(CommunicationInformationValidationMessage.COMM_INFO_SAVED_SUCCESS);
        log.info("CommunicationInformationImpl -> saveUpdateCommunicationInformation() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get active communication information by id
     *
     * @param id - communication information id
     * @return - Active communication information data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getActiveCommunicationInformationById(Long id) {
        log.info("CommunicationInformationImpl -> getActiveCommunicationInformationById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        CommunicationInformation communicationInformation = communicationInformationRepository.getCommunicationInformationByIdAndCommonStatus(id, CommonStatus.ACTIVE.getValue());
        if (communicationInformation != null) {
            log.info("CommunicationInformationImpl -> getActiveCommunicationInformationById() => communication information found!");
            CommunicationInformationDTO dto = new CommunicationInformationDTO();
            dto = communicationInformationMapper.toDto(dto, communicationInformation);
            commonResponse.setMessage(CommunicationInformationValidationMessage.COMM_INFO_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("CommunicationInformationImpl -> getActiveCommunicationInformationById() => communication information not found!");
            throw new RecordNotFoundException(CommunicationInformationValidationMessage.COMM_INFO_NOT_FOUND);
        }
        log.info("CommunicationInformationImpl -> getActiveCommunicationInformationById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all active communication informations
     *
     * @return - All Active communication informations data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActiveCommunicationInformation() {
        log.info("CommunicationInformationImpl -> getAllActiveCommunicationInformation() => started!");
        CommonResponse commonResponse = new CommonResponse();
        List<CommunicationInformation> communicationInfos = communicationInformationRepository.findAll();
        if (!communicationInfos.isEmpty()) {
            log.info("CommunicationInformationImpl -> getAllActiveCommunicationInformation() => communication information found!");
            List<CommunicationInformationDTO> communicationInformationDTOList = communicationInfos.stream()
                    .filter(communicationInformation -> communicationInformation.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                    .map(communicationInformation -> {
                        CommunicationInformationDTO dto = new CommunicationInformationDTO();
                        return communicationInformationMapper.toDto(dto, communicationInformation);
                    })
                    .toList();
            commonResponse.setMessage(CommunicationInformationValidationMessage.COMM_INFOs_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(communicationInformationDTOList);
        } else {
            log.info("CommunicationInformationImpl -> getAllActiveCommunicationInformation() => communication information not found!");
            throw new RecordNotFoundException(CommunicationInformationValidationMessage.COMM_INFO_NOT_FOUND);
        }
        log.info("CommunicationInformationImpl -> getAllActiveCommunicationInformation() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete communication information by id
     *
     * @param id - communication information id
     * @return - communication information deleted success info.
     * @author maleeshasa
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deleteCommunicationInformationById(Long id) {
        log.info("CommunicationInformationImpl -> deleteCommunicationInformationById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        Optional<CommunicationInformation> communicationInfo = communicationInformationRepository.findById(id);
        if (communicationInfo.isPresent()) {
            log.info("CommunicationInformationImpl -> deleteCommunicationInformationById() => communication information found!");
            communicationInfo.get().setCommonStatus(CommonStatus.DELETED.getValue());
            communicationInfo.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            communicationInfo.get().getAuditData().setUpdatedBy(Long.valueOf(1));
            communicationInformationRepository.save(communicationInfo.get());
            commonResponse.setMessage(CommunicationInformationValidationMessage.COMM_INFO_DELETED_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("CommunicationInformationImpl -> deleteCommunicationInformationById() => communication information not found!");
            throw new RecordNotFoundException(CommunicationInformationValidationMessage.COMM_INFO_NOT_FOUND);
        }
        log.info("CommunicationInformationImpl -> deleteCommunicationInformationById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

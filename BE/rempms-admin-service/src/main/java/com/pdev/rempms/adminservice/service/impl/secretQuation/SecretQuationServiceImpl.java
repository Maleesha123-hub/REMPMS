package com.pdev.rempms.adminservice.service.impl.secretQuation;

import com.pdev.rempms.adminservice.constants.enums.CommonStatus;
import com.pdev.rempms.adminservice.constants.validation.SecretQuationValidationMessages;
import com.pdev.rempms.adminservice.dto.secretQuation.SecretQuationDTO;
import com.pdev.rempms.adminservice.exception.RecordNotFoundException;
import com.pdev.rempms.adminservice.mapper.secretQuation.SecretQuationMapper;
import com.pdev.rempms.adminservice.model.AuditData;
import com.pdev.rempms.adminservice.model.SecretQuation;
import com.pdev.rempms.adminservice.repository.SecretQuationRepository;
import com.pdev.rempms.adminservice.service.secretQuation.SecretQuationService;
import com.pdev.rempms.adminservice.util.CommonResponse;
import com.pdev.rempms.adminservice.util.CommonValidation;
import com.pdev.rempms.adminservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author maleeshasa
 * @Date 29/12/2023
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SecretQuationServiceImpl implements SecretQuationService {

    private final SecretQuationRepository secretQuationRepository;
    private final SecretQuationMapper secretQuationMapper;

    /**
     * save or update secret quation
     *
     * @param dto - secret quation saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> saveUpdate(SecretQuationDTO dto) {
        log.info("SecretQuationServiceImpl -> saveUpdate() => started!");
        CommonResponse commonResponse = new CommonResponse();
        SecretQuation secretQuation = new SecretQuation();
        if (!CommonValidation.stringNullValidation(dto.getIdSecretQuation())) {
            log.info("SecretQuationServiceImpl -> saveUpdate() => Update existing secret quation!");
            secretQuation = secretQuationRepository.findById(Long.valueOf(dto.getIdSecretQuation())).get();
            secretQuation = secretQuationMapper.toEntity(secretQuation, dto);
            secretQuation.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            secretQuation.getAuditData().setUpdatedBy(Long.valueOf(1)); //need further development for authorization
        } else {
            log.info("SecretQuationServiceImpl -> saveUpdate() => Save new secret quation!");
            AuditData auditData = new AuditData();
            auditData.setCreatedOn(DateTimeUtil.getSriLankaTime());
            auditData.setCreatedBy(Long.valueOf(1)); //need further development for authorization
            secretQuation.setAuditData(auditData);
            secretQuation = secretQuationMapper.toEntity(secretQuation, dto);
        }
        secretQuationRepository.save(secretQuation);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(SecretQuationValidationMessages.SECRET_QUATION_SAVE_SUCCESS);
        log.info("SecretQuationServiceImpl -> saveUpdate() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get active secret quetion by id
     *
     * @param idSecretQuetion - secret quetion id
     * @return - Active secret quetion data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getActiveById(Long idSecretQuetion) {
        log.info("SecretQuationServiceImpl -> getActiveById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        SecretQuation secretQuation = secretQuationRepository.findByIdAndCommonStatus(idSecretQuetion, CommonStatus.ACTIVE.getValue());
        if (secretQuation != null) {
            log.info("SecretQuationServiceImpl -> getActiveById() => Secret quation found!");
            SecretQuationDTO dto = new SecretQuationDTO();
            dto = secretQuationMapper.toDto(dto, secretQuation);
            commonResponse.setMessage(SecretQuationValidationMessages.SECRET_QUATION_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("SecretQuationServiceImpl -> getActiveById() => Secret quation not found!");
            throw new RecordNotFoundException(SecretQuationValidationMessages.SECRET_QUATION_NOT_FOUND);
        }
        log.info("SecretQuationServiceImpl -> getActiveById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all active secret quations
     *
     * @return - All Active secret quations data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActive() {
        log.info("SecretQuationServiceImpl -> getAllActive() => started.");
        CommonResponse commonResponse = new CommonResponse();
        List<SecretQuation> secretQuations = secretQuationRepository.findAll();
        if (!secretQuations.isEmpty()) {
            log.info("SecretQuationServiceImpl -> getAllActive() => Secret quations found.");
            List<SecretQuationDTO> secretQuationDTOList = secretQuations.stream()
                    .filter(secretQuation -> secretQuation.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                    .map(secretQuation -> {
                        SecretQuationDTO dto = new SecretQuationDTO();
                        return secretQuationMapper.toDto(dto, secretQuation);
                    })
                    .toList();
            commonResponse.setMessage(SecretQuationValidationMessages.SECRET_QUATIONS_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(secretQuationDTOList);
        } else {
            log.info("SecretQuationServiceImpl -> getAllActive() => Secret quations not found.");
            throw new RecordNotFoundException(SecretQuationValidationMessages.SECRET_QUATIONS_NOT_FOUND);
        }
        log.info("SecretQuationServiceImpl -> getAllActive() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete secret quation by secret quation id
     *
     * @param idSecretQuation - secret quation id
     * @return - secret quation deleted success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> deleteById(Long idSecretQuation) {
        log.info("SecretQuationServiceImpl -> deleteById() => started.");
        CommonResponse commonResponse = new CommonResponse();
        Optional<SecretQuation> secretQuation = secretQuationRepository.findById(idSecretQuation);
        if (secretQuation.isPresent()) {
            log.info("SecretQuationServiceImpl -> deleteById() => Secret quation found.");
            secretQuation.get().setCommonStatus(CommonStatus.DELETED.getValue());
            secretQuation.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            secretQuation.get().getAuditData().setUpdatedBy(Long.valueOf(1)); //need further development for authorization
            secretQuationRepository.save(secretQuation.get());
            commonResponse.setMessage(SecretQuationValidationMessages.SECRET_QUATION_DELETE_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("SecretQuationServiceImpl -> deleteById() => Secret quation not found.");
            throw new RecordNotFoundException(SecretQuationValidationMessages.SECRET_QUATION_NOT_FOUND);
        }
        log.info("SecretQuationServiceImpl -> deleteById() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

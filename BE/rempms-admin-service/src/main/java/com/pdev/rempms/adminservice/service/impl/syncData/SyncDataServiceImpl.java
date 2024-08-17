package com.pdev.rempms.adminservice.service.impl.syncData;

import com.pdev.rempms.adminservice.constants.enums.CommonStatus;
import com.pdev.rempms.adminservice.constants.validation.SecretQuationValidationMessages;
import com.pdev.rempms.adminservice.constants.validation.SyncDataValidationMessages;
import com.pdev.rempms.adminservice.constants.validation.UserAccountValidationMessage;
import com.pdev.rempms.adminservice.constants.validation.UserRoleValidationMessage;
import com.pdev.rempms.adminservice.dto.secretQuation.SecretQuationDTO;
import com.pdev.rempms.adminservice.dto.syncData.SyncDataRequestDTO;
import com.pdev.rempms.adminservice.dto.userAccount.UserAccountDTO;
import com.pdev.rempms.adminservice.dto.userRole.UserRoleDTO;
import com.pdev.rempms.adminservice.exception.FeignCustomException;
import com.pdev.rempms.adminservice.mapper.secretQuation.SecretQuationMapper;
import com.pdev.rempms.adminservice.mapper.userAccount.UserAccountMapper;
import com.pdev.rempms.adminservice.mapper.userRole.UserRoleMapper;
import com.pdev.rempms.adminservice.proxyClient.UserServiceClient;
import com.pdev.rempms.adminservice.repository.SecretQuationRepository;
import com.pdev.rempms.adminservice.repository.UserAccountRepository;
import com.pdev.rempms.adminservice.repository.UserRoleRepository;
import com.pdev.rempms.adminservice.service.syncData.SyncDataService;
import com.pdev.rempms.adminservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SyncDataServiceImpl implements SyncDataService {

    private final UserRoleRepository userRoleRepository;
    private final UserAccountRepository userAccountRepository;
    private final SecretQuationRepository secretQuationRepository;
    private final UserServiceClient userServiceClient;
    private final SecretQuationMapper secretQuationMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserAccountMapper userAccountMapper;

    /**
     * sync role and user data
     *
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> syncUserData() {
        log.info("SyncDataServiceImpl -> syncUserData() => started.");
        CommonResponse commonResponse = new CommonResponse();
        SyncDataRequestDTO syncDataRequestDTO = new SyncDataRequestDTO();

        List<UserRoleDTO> userRoles = userRoleRepository.findAll().stream()
                .filter(userRole -> userRole.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                .map(userRole -> {
                    UserRoleDTO dto = new UserRoleDTO();
                    return userRoleMapper.toSyncDto(dto, userRole);
                })
                .toList();
        List<UserAccountDTO> userAccounts = userAccountRepository.findByCommonStatus(CommonStatus.ACTIVE.getValue()).stream()
                .map(userAccount -> {
                    UserAccountDTO dto = new UserAccountDTO();
                    return userAccountMapper.toSyncDto(dto, userAccount);
                }).toList();

        String validation = validateUserSyncData(userRoles, userAccounts);
        if (validation != null) {
            log.info("SyncDataServiceImpl -> syncUserData() => Validation available!");
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(validation);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        try {
            syncDataRequestDTO.setUserRoles(userRoles);
            syncDataRequestDTO.setUserAccounts(userAccounts);
            CommonResponse response = userServiceClient.syncUserData(syncDataRequestDTO);
            commonResponse.setStatus(response.getStatus());
            commonResponse.setMessage(response.getMessage());
        } catch (FeignCustomException ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(SyncDataValidationMessages.SYNC_FAIL);
            log.error("Sync users and roles data for user service {} " + "body :" + ex.getBody() + "message :" + ex.getMessage());
        }
        log.info("SyncDataServiceImpl -> syncUserData() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Validate sync data for user and roles
     *
     * @param userRoles
     * @param userAccounts
     * @return - Validation messages
     * @author maleeshasa
     */
    private String validateUserSyncData(List<UserRoleDTO> userRoles, List<UserAccountDTO> userAccounts) {
        log.info("SyncDataServiceImpl -> validateUserSyncData() => started!");
        String validation = null;
        if (userRoles.isEmpty()) {
            validation = UserRoleValidationMessage.USER_ROLES_NOT_FOUND;
        } else if (userAccounts.isEmpty()) {
            validation = UserAccountValidationMessage.USERS_NOT_FOUND;
        }
        log.info("SyncDataServiceImpl -> validateUserSyncData() => ended!");
        return validation;
    }

    /**
     * sync secret quation data
     *
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> syncSecQuation() {
        log.info("SyncDataServiceImpl -> syncSecQuation() => started!");
        CommonResponse commonResponse = new CommonResponse();
        List<SecretQuationDTO> secretQuations = secretQuationRepository.findByCommonStatus(CommonStatus.ACTIVE.getValue()).stream()
                .map(secretQuation -> {
                    SecretQuationDTO dto = new SecretQuationDTO();
                    return secretQuationMapper.toSyncDto(dto, secretQuation);
                }).toList();
        String validation = validateSecQuetionSyncData(secretQuations);
        if (validation != null) {
            log.info("SyncDataServiceImpl -> syncSecQuation() => Validation available!");
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(validation);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        try {
            CommonResponse response = userServiceClient.syncSecQuationData(secretQuations);
            commonResponse.setStatus(response.getStatus());
            commonResponse.setMessage(response.getMessage());
        } catch (FeignCustomException ex) {
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(SyncDataValidationMessages.SYNC_FAIL);
            log.error("Sync secret quetions data for user service {} " + "body :" + ex.getBody() + "message :" + ex.getMessage());

        }
        log.info("SyncDataServiceImpl -> syncSecQuation() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Validate secret quation sync data
     *
     * @param secretQuations
     * @return - Validation messages
     * @author maleeshasa
     */
    private String validateSecQuetionSyncData(List<SecretQuationDTO> secretQuations) {
        log.info("SyncDataServiceImpl -> validateSecQuetionSyncData() => started!");
        String validation = null;
        if (secretQuations.isEmpty()) {
            validation = SecretQuationValidationMessages.SECRET_QUATIONS_NOT_FOUND;
        }
        log.info("SyncDataServiceImpl -> validateSecQuetionSyncData() => ended!");
        return validation;
    }
}

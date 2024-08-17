package com.pdev.rempms.adminservice.service.impl.userRole;

import com.pdev.rempms.adminservice.constants.enums.CommonStatus;
import com.pdev.rempms.adminservice.constants.validation.UserRoleValidationMessage;
import com.pdev.rempms.adminservice.dto.userRole.UserRoleDTO;
import com.pdev.rempms.adminservice.exception.RecordNotFoundException;
import com.pdev.rempms.adminservice.mapper.userRole.UserRoleMapper;
import com.pdev.rempms.adminservice.model.AuditData;
import com.pdev.rempms.adminservice.model.UserRole;
import com.pdev.rempms.adminservice.repository.UserRoleRepository;
import com.pdev.rempms.adminservice.service.userRole.UserRoleService;
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
 * @Date 16/12/2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;

    /**
     * save or update user role
     *
     * @param dto - user role saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> saveUpdateUserRole(UserRoleDTO dto) {
        log.info("UserRoleServiceImpl -> saveUpdateUserRole() => started!");
        CommonResponse commonResponse = new CommonResponse();
        UserRole userRole = new UserRole();
        if (!CommonValidation.stringNullValidation(dto.getIdUserRole())) {
            log.info("UserRoleServiceImpl -> saveUpdateUserRole() => Update existing user role!");
            userRole = userRoleRepository.findById(Long.valueOf(dto.getIdUserRole())).get();
            userRole = userRoleMapper.toEntity(userRole, dto);
            userRole.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            userRole.getAuditData().setUpdatedBy(Long.valueOf(1)); //need further development for authorization
        } else {
            log.info("UserRoleServiceImpl -> saveUpdateUserRole() => Save new user role!");
            AuditData auditData = new AuditData();
            auditData.setCreatedOn(DateTimeUtil.getSriLankaTime());
            auditData.setCreatedBy(Long.valueOf(1)); //need further development for authorization
            userRole.setAuditData(auditData);
            userRole = userRoleMapper.toEntity(userRole, dto);
        }
        userRoleRepository.save(userRole);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(UserRoleValidationMessage.USER_ROLE_SAVED_SUCCESS);
        log.info("UserRoleServiceImpl -> saveUpdateUserRole() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get active user role by id
     *
     * @param idUserRole - user role id
     * @return - Active user role data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getActiveUserRoleById(Long idUserRole) {
        log.info("UserRoleServiceImpl -> getActiveUserRoleById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        UserRole userRole = userRoleRepository.findByIdAndCommonStatus(idUserRole, CommonStatus.ACTIVE.getValue());
        if (userRole != null) {
            log.info("UserRoleServiceImpl -> getActiveUserRoleById() => User role found!");
            UserRoleDTO dto = new UserRoleDTO();
            dto = userRoleMapper.toDto(dto, userRole);
            commonResponse.setMessage(UserRoleValidationMessage.USER_ROLE_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("UserRoleServiceImpl -> getActiveUserRoleById() => User role not found!");
            throw new RecordNotFoundException(UserRoleValidationMessage.USER_ROLE_NOT_FOUND);
        }
        log.info("UserRoleServiceImpl -> getActiveUserRoleById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all active user roles
     *
     * @return - All Active user roles data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActiveUserRoles() {
        log.info("UserRoleServiceImpl -> getAllActiveUserRoles() => started.");
        CommonResponse commonResponse = new CommonResponse();
        List<UserRole> userRoles = userRoleRepository.findAll();
        if (!userRoles.isEmpty()) {
            log.info("UserRoleServiceImpl -> getAllActiveUserRoles() => User roles found.");
            List<UserRoleDTO> userRoleDTOList = userRoles.stream()
                    .filter(userRole -> userRole.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                    .map(userRole -> {
                        UserRoleDTO dto = new UserRoleDTO();
                        return userRoleMapper.toDto(dto, userRole);
                    })
                    .toList();
            commonResponse.setMessage(UserRoleValidationMessage.USER_ROLES_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(userRoleDTOList);
        } else {
            log.info("UserRoleServiceImpl -> getAllActiveUserRoles() => User roles not found.");
            throw new RecordNotFoundException(UserRoleValidationMessage.USER_ROLES_NOT_FOUND);
        }
        log.info("UserRoleServiceImpl -> getAllActiveUserRoles() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete user role by user role id
     *
     * @param idUserRole - user role id
     * @return - user role deleted success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> deleteUserRoleById(Long idUserRole) {
        log.info("UserRoleServiceImpl -> deleteUserRoleById() => started.");
        CommonResponse commonResponse = new CommonResponse();
        Optional<UserRole> userRole = userRoleRepository.findById(idUserRole);
        if (userRole.isPresent()) {
            log.info("UserRoleServiceImpl -> deleteUserRoleById() => City found.");
            userRole.get().setCommonStatus(CommonStatus.DELETED.getValue());
            userRole.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            userRole.get().getAuditData().setUpdatedBy(Long.valueOf(1)); //need further development for authorization
            userRoleRepository.save(userRole.get());
            commonResponse.setMessage(UserRoleValidationMessage.USER_ROLE_DELETED_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("UserRoleServiceImpl -> deleteUserRoleById() => City not found.");
            throw new RecordNotFoundException(UserRoleValidationMessage.USER_ROLE_NOT_FOUND);
        }
        log.info("UserRoleServiceImpl -> deleteUserRoleById() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

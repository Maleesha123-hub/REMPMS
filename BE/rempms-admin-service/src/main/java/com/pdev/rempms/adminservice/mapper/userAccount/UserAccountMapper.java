package com.pdev.rempms.adminservice.mapper.userAccount;

import com.pdev.rempms.adminservice.dto.userAccount.UserAccountDTO;
import com.pdev.rempms.adminservice.dto.userRole.UserRoleDTO;
import com.pdev.rempms.adminservice.mapper.userRole.UserRoleMapper;
import com.pdev.rempms.adminservice.model.UserAccount;
import com.pdev.rempms.adminservice.model.UserRole;
import com.pdev.rempms.adminservice.util.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserAccountMapper {

    private final UserRoleMapper userRoleMapper;

    public UserAccountDTO toDto(UserAccountDTO dto, UserAccount userAccount) {
        log.info("UserAccountMapper -> toDto() => started!");
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        dto.setIdUserAccount(String.valueOf(userAccount.getId()));
        dto.setUserName(userAccount.getUserName());
        dto.setHiddenUserImageName(userAccount.getUserImage());
        dto.setCommonStatus(userAccount.getCommonStatus());
        dto.setUserRole(userRoleMapper.toDto(userRoleDTO, userAccount.getUserRole()));
        log.info("UserAccountMapper -> toDto() => ended!");
        return dto;
    }

    public UserAccount toEntity(UserAccount userAccount, UserRole userRole, UserAccountDTO dto) {
        log.info("UserAccountMapper -> toEntity() => started!");
        if (!CommonValidation.stringNullValidation(dto.getIdUserAccount())) {
            userAccount.setId(Long.valueOf(dto.getIdUserAccount()));
        }
        userAccount.setUserName(dto.getUserName());
        userAccount.setUserImage(dto.getHiddenUserImageName());
        userAccount.setCommonStatus(dto.getCommonStatus());
        userAccount.setUserRole(userRole);
        log.info("UserAccountMapper -> toEntity() => ended!");
        return userAccount;
    }

    public UserAccountDTO toSyncDto(UserAccountDTO dto, UserAccount userAccount) {
        log.info("UserAccountMapper -> toSyncDto() => started!");
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        dto.setIdUserAccount(String.valueOf(userAccount.getId()));
        dto.setUserName(userAccount.getUserName());
        dto.setHiddenUserImageName(userAccount.getUserImage());
        dto.setCommonStatus(userAccount.getCommonStatus());
        dto.setPassword(userAccount.getPassword());
        dto.setCreatedBy(String.valueOf(userAccount.getAuditData().getCreatedBy()));
        dto.setCreatedOn(String.valueOf(userAccount.getAuditData().getCreatedOn()));
        if (userAccount.getAuditData().getUpdatedBy() != null) {
            dto.setUpdatedBy(String.valueOf(userAccount.getAuditData().getUpdatedBy()));
        }
        if (userAccount.getAuditData().getUpdatedOn() != null) {
            dto.setUpdatedOn(String.valueOf(userAccount.getAuditData().getUpdatedOn()));
        }
        dto.setUserRole(userRoleMapper.toSyncDto(userRoleDTO, userAccount.getUserRole()));
        log.info("UserAccountMapper -> toSyncDto() => ended!");
        return dto;
    }
}

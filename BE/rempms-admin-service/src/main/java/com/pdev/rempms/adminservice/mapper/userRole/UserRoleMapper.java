package com.pdev.rempms.adminservice.mapper.userRole;

import com.pdev.rempms.adminservice.dto.userRole.UserRoleDTO;
import com.pdev.rempms.adminservice.model.UserRole;
import com.pdev.rempms.adminservice.util.CommonValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
@Component
@Slf4j
public class UserRoleMapper {

    public UserRoleDTO toDto(UserRoleDTO dto, UserRole userRole) {
        log.info("UserRoleMapper -> toDto() => started!");
        dto.setIdUserRole(String.valueOf(userRole.getId()));
        dto.setUserRoleName(userRole.getUserRoleName());
        dto.setUserRoleDescription(userRole.getUserRoleDescription());
        dto.setCommonStatus(userRole.getCommonStatus());
        log.info("UserRoleMapper -> toDto() => ended!");
        return dto;
    }

    public UserRole toEntity(UserRole userRole, UserRoleDTO dto) {
        log.info("UserRoleMapper -> toEntity() => started!");
        if (!CommonValidation.stringNullValidation(dto.getIdUserRole())) {
            userRole.setId(Long.valueOf(dto.getIdUserRole()));
        }
        userRole.setUserRoleName(dto.getUserRoleName());
        userRole.setUserRoleDescription(dto.getUserRoleDescription());
        userRole.setCommonStatus(dto.getCommonStatus());
        log.info("UserRoleMapper -> toEntity() => ended!");
        return userRole;
    }

    public UserRoleDTO toSyncDto(UserRoleDTO dto, UserRole userRole) {
        log.info("UserRoleMapper -> toSyncDto() => started!");
        dto.setIdUserRole(String.valueOf(userRole.getId()));
        dto.setUserRoleName(userRole.getUserRoleName());
        dto.setUserRoleDescription(userRole.getUserRoleDescription());
        dto.setCommonStatus(userRole.getCommonStatus());
        dto.setCreatedBy(String.valueOf(userRole.getAuditData().getCreatedBy()));
        dto.setCreatedOn(String.valueOf(userRole.getAuditData().getCreatedOn()));
        if (userRole.getAuditData().getUpdatedBy() != null) {
            dto.setUpdatedBy(String.valueOf(userRole.getAuditData().getUpdatedBy()));
        }
        if (userRole.getAuditData().getUpdatedOn() != null) {
            dto.setUpdatedOn(String.valueOf(userRole.getAuditData().getUpdatedOn()));
        }
        log.info("UserRoleMapper -> toSyncDto() => ended!");
        return dto;
    }
}

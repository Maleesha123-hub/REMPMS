package com.pdev.rempms.adminservice.dto.userRole;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
@Data
public class UserRoleDTO {

    private String idUserRole;

    @NotNull(message = "User role can not be null.")
    @NotEmpty(message = "User role can not be empty.")
    private String userRoleName;

    @NotNull(message = "User role description can not be null.")
    private String userRoleDescription;
    private String commonStatus;
    private String createdBy;
    private String createdOn;
    private String updatedBy;
    private String updatedOn;
}

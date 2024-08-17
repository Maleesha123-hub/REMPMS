package com.pdev.rempms.adminservice.dto.userAccount;

import com.pdev.rempms.adminservice.dto.userRole.UserRoleDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
@Data
public class UserAccountDTO {
    private String idUserAccount;

    @NotNull(message = "User name can not be null.")
    @NotEmpty(message = "User name can not be empty.")
    private String userName;

    private String userImage;

    private String hiddenUserImageName;
    private String password;
    private String token;
    private String commonStatus;
    private String idUserRole;
    private UserRoleDTO userRole;
    private String createdBy;
    private String createdOn;
    private String updatedBy;
    private String updatedOn;
}

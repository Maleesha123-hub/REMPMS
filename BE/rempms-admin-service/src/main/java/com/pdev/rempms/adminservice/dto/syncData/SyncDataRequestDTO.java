package com.pdev.rempms.adminservice.dto.syncData;

import com.pdev.rempms.adminservice.dto.userAccount.UserAccountDTO;
import com.pdev.rempms.adminservice.dto.userRole.UserRoleDTO;
import lombok.Data;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
@Data
public class SyncDataRequestDTO {
    private List<UserRoleDTO> userRoles;
    private List<UserAccountDTO> userAccounts;
}

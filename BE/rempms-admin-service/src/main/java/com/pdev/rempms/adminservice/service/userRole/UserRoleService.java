package com.pdev.rempms.adminservice.service.userRole;

import com.pdev.rempms.adminservice.dto.userRole.UserRoleDTO;
import com.pdev.rempms.adminservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
public interface UserRoleService {

    /**
     * save or update user role
     *
     * @param dto - user role saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdateUserRole(UserRoleDTO dto);

    /**
     * Get active user role by id
     *
     * @param idUserRole - user role id
     * @return - Active user role data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActiveUserRoleById(Long idUserRole);

    /**
     * Get all active user roles
     *
     * @return - All Active user roles data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActiveUserRoles();

    /**
     * Delete user role by user role id
     *
     * @param idUserRole - user role id
     * @return - user role deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deleteUserRoleById(Long idUserRole);
}

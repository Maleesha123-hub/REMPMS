package com.pdev.rempms.adminservice.controller;

import com.pdev.rempms.adminservice.dto.userRole.UserRoleDTO;
import com.pdev.rempms.adminservice.service.userRole.UserRoleService;
import com.pdev.rempms.adminservice.util.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/user-role")
public class UserRoleController {

    private final UserRoleService userRoleService;

    /**
     * save or update user role
     *
     * @param dto - user role saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdateUserRole(@Valid @RequestBody UserRoleDTO dto) {
        return userRoleService.saveUpdateUserRole(dto);
    }

    /**
     * Get active user role by id
     *
     * @param idUserRole - user role id
     * @return - Active user role data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getById/{idUserRole}")
    public ResponseEntity<CommonResponse> getActiveUserRoleById(@PathVariable Long idUserRole) {
        return userRoleService.getActiveUserRoleById(idUserRole);
    }

    /**
     * Get all active user roles
     *
     * @return - All Active user roles data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActiveUserRoles() {
        return userRoleService.getAllActiveUserRoles();
    }

    /**
     * Delete user role by user role id
     *
     * @param idUserRole - user role id
     * @return - user role deleted success info.
     * @author maleeshasa
     */
    @GetMapping(value = "/deleteById/{idUserRole}")
    public ResponseEntity<CommonResponse> deleteUserRoleById(@PathVariable Long idUserRole) {
        return userRoleService.deleteUserRoleById(idUserRole);
    }
}

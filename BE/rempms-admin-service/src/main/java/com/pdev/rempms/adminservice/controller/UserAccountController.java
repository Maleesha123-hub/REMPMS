package com.pdev.rempms.adminservice.controller;

import com.pdev.rempms.adminservice.dto.userAccount.UserAccountDTO;
import com.pdev.rempms.adminservice.service.userAccount.UserAccountService;
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
@RequestMapping("api/v1/admin/user-account")
public class UserAccountController {

    private final UserAccountService userAccountService;

    /**
     * save or update user account
     *
     * @param dto - user account saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdateUserAccount(@Valid @RequestBody UserAccountDTO dto) {
        return userAccountService.saveUpdateUserAccount(dto);
    }

    /**
     * Get active user account by id
     *
     * @param idUserAccount - user account id
     * @return - Active user account data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getById/{idUserAccount}")
    public ResponseEntity<CommonResponse> getActiveUserAccountById(@PathVariable Long idUserAccount) {
        return userAccountService.getActiveUserAccountById(idUserAccount);
    }

    /**
     * Get all active user accounts
     *
     * @return - All Active user accounts data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActiveUserAccounts() {
        return userAccountService.getAllActiveUserAccounts();
    }

    /**
     * Delete user account by user account id
     *
     * @param idUserAccount - user account id
     * @return - User account deleted success info.
     * @author maleeshasa
     */
    @GetMapping(value = "/deleteById/{idUserAccount}")
    public ResponseEntity<CommonResponse> deleteUserAccountById(@PathVariable Long idUserAccount) {
        return userAccountService.deleteUserAccountById(idUserAccount);
    }
}

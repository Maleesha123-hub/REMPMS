package com.pdev.rempms.adminservice.service.userAccount;

import com.pdev.rempms.adminservice.dto.userAccount.UserAccountDTO;
import com.pdev.rempms.adminservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
public interface UserAccountService {

    /**
     * save or update user account
     *
     * @param dto - user account saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdateUserAccount(UserAccountDTO dto);

    /**
     * Get active user account by id
     *
     * @param idUserAccount - user account id
     * @return - Active user account data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActiveUserAccountById(Long idUserAccount);

    /**
     * Get all active user accounts
     *
     * @return - All Active user accounts data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActiveUserAccounts();

    /**
     * Delete user account by user account id
     *
     * @param idUserAccount - user account id
     * @return - User account deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deleteUserAccountById(Long idUserAccount);
}

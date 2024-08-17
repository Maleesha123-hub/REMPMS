package com.pdev.rempms.adminservice.service.impl.userAccount;

import com.pdev.rempms.adminservice.constants.enums.CommonStatus;
import com.pdev.rempms.adminservice.constants.validation.CommonValidationMessage;
import com.pdev.rempms.adminservice.constants.validation.UserAccountValidationMessage;
import com.pdev.rempms.adminservice.constants.validation.UserRoleValidationMessage;
import com.pdev.rempms.adminservice.dto.userAccount.UserAccountDTO;
import com.pdev.rempms.adminservice.exception.RecordNotFoundException;
import com.pdev.rempms.adminservice.mapper.userAccount.UserAccountMapper;
import com.pdev.rempms.adminservice.model.AuditData;
import com.pdev.rempms.adminservice.model.UserAccount;
import com.pdev.rempms.adminservice.model.UserRole;
import com.pdev.rempms.adminservice.repository.UserAccountRepository;
import com.pdev.rempms.adminservice.repository.UserRoleRepository;
import com.pdev.rempms.adminservice.service.userAccount.UserAccountService;
import com.pdev.rempms.adminservice.util.CommonResponse;
import com.pdev.rempms.adminservice.util.CommonValidation;
import com.pdev.rempms.adminservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserAccountMapper userAccountMapper;

    /**
     * save or update user account
     *
     * @param dto - user account saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> saveUpdateUserAccount(UserAccountDTO dto) {
        log.info("UserAccountServiceImpl -> saveUpdateUserAccount() => started!");
        CommonResponse commonResponse = new CommonResponse();
        UserAccount userAccount = new UserAccount();
        String validation = ValidateUserAccount(dto);
        if (validation != null) {
            log.info("UserAccountServiceImpl -> saveUpdateUserAccount() => Validation available!");
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(validation);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        UserRole userRole = userRoleRepository.findById(Long.valueOf(dto.getIdUserRole())).get();
        String encryptedPassword = getCryptPassword(dto.getPassword());
        if (!CommonValidation.stringNullValidation(dto.getIdUserAccount())) {
            log.info("UserAccountServiceImpl -> saveUpdateUserAccount() => Update existing user account!");
            userRole = userRoleRepository.findById(Long.valueOf(dto.getIdUserRole())).get();
            userAccount = userAccountRepository.findById(Long.valueOf(dto.getIdUserAccount())).get();
            userAccount = userAccountMapper.toEntity(userAccount, userRole, dto);
            userAccount.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            userAccount.getAuditData().setUpdatedBy(Long.valueOf(1)); //need further development for authorization
        } else {
            log.info("UserAccountServiceImpl -> saveUpdateUserAccount() => Save new user account!");
            AuditData auditData = new AuditData();
            auditData.setCreatedOn(DateTimeUtil.getSriLankaTime());
            auditData.setCreatedBy(Long.valueOf(1)); //need further development for authorization
            userAccount.setAuditData(auditData);
            userAccount = userAccountMapper.toEntity(userAccount, userRole, dto);
        }
        userAccount.setPassword(encryptedPassword);
        userAccountRepository.save(userAccount);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(UserAccountValidationMessage.USER_SAVED_SUCCESS);
        log.info("UserAccountServiceImpl -> saveUpdateUserAccount() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Validate user account save data
     *
     * @param dto
     * @return - Validation messages
     * @author maleeshasa
     */
    private String ValidateUserAccount(UserAccountDTO dto) {
        log.info("UserAccountServiceImpl -> validateUserAccount() => started!");
        String validation = null;
        if (CommonValidation.stringNullValidation(dto.getIdUserAccount()) &&
                CommonValidation.stringNullValidation(dto.getUserImage())) {
            validation = UserAccountValidationMessage.USER_IMAGE_EMPTY;
        } else if (CommonValidation.stringNullValidation(dto.getUserName())) {
            validation = UserAccountValidationMessage.USER_NAME_EMPTY;
        } else if (CommonValidation.stringNullValidation(dto.getPassword())) {
            validation = UserAccountValidationMessage.USER_PASSWORD_EMPTY;
        } else if (dto.getIdUserRole().equalsIgnoreCase("-1")) {
            validation = UserRoleValidationMessage.USER_ROLE_NOT_FOUND;
        } else if (dto.getCommonStatus().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COMMON_STATUS_EMPTY;
        }
        log.info("UserAccountServiceImpl -> validateUserAccount() => ended!");
        return validation;
    }

    /**
     * Get active user account by id
     *
     * @param idUserAccount - user account id
     * @return - Active user account data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getActiveUserAccountById(Long idUserAccount) {
        log.info("UserAccountServiceImpl -> getActiveUserAccountById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        UserAccount userAccount = userAccountRepository.findByIdAndCommonStatus(idUserAccount, CommonStatus.ACTIVE.getValue());
        if (userAccount != null) {
            log.info("UserAccountServiceImpl -> getActiveUserAccountById() => User account found!");
            UserAccountDTO dto = new UserAccountDTO();
            dto = userAccountMapper.toDto(dto, userAccount);
            commonResponse.setMessage(UserAccountValidationMessage.USER_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("UserAccountServiceImpl -> getActiveUserAccountById() => User account not found!");
            throw new RecordNotFoundException(UserAccountValidationMessage.USER_NOT_FOUND);
        }
        log.info("UserAccountServiceImpl -> getActiveUserAccountById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all active user accounts
     *
     * @return - All Active user accounts data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActiveUserAccounts() {
        log.info("UserAccountServiceImpl -> getAllActiveUserAccounts() => started.");
        CommonResponse commonResponse = new CommonResponse();
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        if (!userAccounts.isEmpty()) {
            log.info("UserAccountServiceImpl -> getAllActiveUserAccounts() => User roles found.");
            List<UserAccountDTO> userAccountDTOList = userAccounts.stream()
                    .filter(userAccount -> userAccount.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                    .map(userAccount -> {
                        UserAccountDTO dto = new UserAccountDTO();
                        return userAccountMapper.toDto(dto, userAccount);
                    })
                    .toList();
            commonResponse.setMessage(UserAccountValidationMessage.USERS_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(userAccountDTOList);
        } else {
            log.info("UserAccountServiceImpl -> getAllActiveUserAccounts() => User roles not found.");
            throw new RecordNotFoundException(UserAccountValidationMessage.USERS_NOT_FOUND);
        }
        log.info("UserAccountServiceImpl -> getAllActiveUserAccounts() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete user account by user account id
     *
     * @param idUserAccount - user account id
     * @return - User account deleted success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> deleteUserAccountById(Long idUserAccount) {
        log.info("UserAccountServiceImpl -> deleteUserAccountById() => started.");
        CommonResponse commonResponse = new CommonResponse();
        Optional<UserAccount> userAccount = userAccountRepository.findById(idUserAccount);
        if (userAccount.isPresent()) {
            log.info("UserAccountServiceImpl -> deleteUserAccountById() => User account found.");
            userAccount.get().setCommonStatus(CommonStatus.DELETED.getValue());
            userAccount.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            userAccount.get().getAuditData().setUpdatedBy(Long.valueOf(1)); //need further development for authorization
            userAccountRepository.save(userAccount.get());
            commonResponse.setMessage(UserAccountValidationMessage.USER_DELETED_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("UserAccountServiceImpl -> deleteUserAccountById() => User account not found.");
            throw new RecordNotFoundException(UserAccountValidationMessage.USER_NOT_FOUND);
        }
        log.info("UserAccountServiceImpl -> deleteUserAccountById() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Encrypt the password
     *
     * @param rawPassword
     * @return - encrypted password
     * @author maleeshasa
     */
    public String getCryptPassword(String rawPassword) {
        log.info("UserAccountServiceImpl -> getCryptPassword() => started.");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        log.info("UserAccountServiceImpl -> getCryptPassword() => ended.");
        return bCryptPasswordEncoder.encode(rawPassword);
    }
}

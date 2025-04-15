package com.pdev.rempms.locationservice.config;

import com.pdev.rempms.locationservice.constants.RolePermissionsConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author @maleeshasa
 * @Date 2024/11/16
 */
@Slf4j
@Component
public class JWTUtil {

    /**
     * This method is allowed to check user has valid authorize party and has permission to access this resource
     *
     * @param azpWithRoles {@link Map<String, List<String>>} - User's authorize parties and roles
     * @return {@link boolean} - is valid or not
     * @author maleeshasa
     */
    public boolean hasRecruitmentPermission(Map<String, List<String>> azpWithRoles) {
        log.info("JWTUtil.hasRecruitmentPermission() => started.");
        if (azpWithRoles != null && !azpWithRoles.isEmpty()) {
            log.info("Checking has the user permissions to access this resources.");
            List<String> permissions = azpWithRoles.get(RolePermissionsConstants.CLIENT_ID_REM_PMS);
            return !permissions.isEmpty() && permissions.contains(RolePermissionsConstants.PERMISSION_LOCATION_SERVICE);
        }
        log.info("JWTUtil.hasRecruitmentPermission() => ended.");
        return false;
    }
}

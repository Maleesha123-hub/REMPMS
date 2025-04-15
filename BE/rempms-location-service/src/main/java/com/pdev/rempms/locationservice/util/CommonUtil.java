package com.pdev.rempms.locationservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CommonUtil {

    private final ObjectMapper objectMapper;

    /**
     * This method is allowed to get the current username from security context
     *
     * @return {@link String} - username
     * @author maleeshasa
     */
    public String getUsername() {
        log.info("CommonUtil.getUsername() => started.");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("CommonUtil.getUsername() => ended.");
        return objectMapper.convertValue(principal, String.class);
    }
}

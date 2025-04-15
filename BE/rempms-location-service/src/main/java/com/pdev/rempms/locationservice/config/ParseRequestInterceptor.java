package com.pdev.rempms.locationservice.config;

import com.pdev.rempms.locationservice.constants.CommonConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Map;

/**
 * @author @maleeshasa
 * @Date 2024/11/16
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class ParseRequestInterceptor {

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {

        return (RequestTemplate requestTemplate) -> {
            Map<String, Collection<String>> headers = requestTemplate.headers();
            if (headers.containsKey("OUT_SERVICE") && headers.get("OUT_SERVICE").contains("out_service")) {
                log.info("Calling outside services for resources.");
            } else {
                // Retrieve the JWT token from the SecurityContext
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication != null && authentication.getCredentials() != null) {
                    String token = (String) authentication.getCredentials(); // Extract token from authentication's credentials
                    requestTemplate.header(CommonConstants.AUTHORIZATION, CommonConstants.BEARER.concat(token)); // Add Bearer token
                    log.info("Bearer token added to the request.");
                }
            }
        };
    }
}

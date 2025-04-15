package com.pdev.rempms.documentservice.config;

import com.pdev.rempms.documentservice.constants.RolePermissionsConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enable @PreAuthorize and @PostAuthorize
public class WebSecurityConfig {

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the Spring Security filter chain for the application. This method defines
     * security policies such as endpoint access rules, session management settings,
     * and the inclusion of custom filters.
     *
     * @param httpSecurity {@link HttpSecurity} - the security configuration object provided by Spring Security
     * @return {@link SecurityFilterChain} - the configured security filter chain, which Spring uses to process incoming requests
     * @configuration: 1. Disables CSRF protection for stateless applications.
     * 2. Defines endpoint-specific access rules:
     * - Public access to health check and authentication endpoints.
     * - Role-based access to user-related endpoints requiring the "PERMISSION_USER_AUTH_SERVICE" authority.
     * - Authentication required for all other endpoints.
     * 3. Sets session management to `STATELESS` mode, as JWTs are used for stateless authentication.
     * 4. Adds a custom JWT authentication filter (`jwtAuthenticationFilter`) before the default
     * `UsernamePasswordAuthenticationFilter`.
     * @author maleeshasa
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("WebSecurityConfig.securityFilterChain() => started.");
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/health/healthChecker").permitAll()
                                .requestMatchers("/api/document/**").hasAuthority(RolePermissionsConstants.PERMISSION_DOCUMENT_SERVICE)
                                .anyRequest().authenticated()
                ).sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        log.info("WebSecurityConfig.securityFilterChain() => ended.");
        return httpSecurity
                .build();
    }
}

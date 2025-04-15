package com.pdev.rempms.locationservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.rempms.locationservice.constants.AuthErrorMessages;
import com.pdev.rempms.locationservice.constants.ClaimsConstant;
import com.pdev.rempms.locationservice.constants.CommonConstants;
import com.pdev.rempms.locationservice.constants.RolePermissionsConstants;
import com.pdev.rempms.locationservice.exception.UnauthorizedException;
import com.pdev.rempms.locationservice.service.rest.KeyCloakClientService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final KeyCloakClientService keyCloakClientService;
    private final JWTUtil jwtUtil;

    /**
     * This method is responsible for intercepting HTTP requests to perform JWT-based authentication and authorization.
     * It validates the JWT, extracts user roles and permissions, and sets the authenticated user's details
     * in the Spring Security context.
     *
     * @param request     {@link HttpServletRequest} - the HTTP request containing the Authorization header with the JWT
     * @param response    {@link HttpServletResponse} - the HTTP response for sending status and error messages
     * @param filterChain {@link FilterChain} - the chain of filters to process the request and response
     * @throws ServletException - if an error occurs during the filter execution
     * @throws IOException      - if an I/O error occurs during request or response handling
     * @author maleeshasa
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        logger.info("JWTAuthenticationFilter.doFilterInternal() => started.");

        final String authHeader = request.getHeader(CommonConstants.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer")) {
            log.info("Validate the token token issuer using the Keycloak client service...");

            Map<String, Object> validatedResponse = keyCloakClientService.validateToken(authHeader);
            String username = (String) validatedResponse.get("username");
            Boolean valid = (Boolean) validatedResponse.get("valid");

            if (Boolean.TRUE.equals(valid)) {
                log.info("Token is valid, proceed to extract roles and permissions");

                Map<String, List<String>> azpWithRoles = (Map<String, List<String>>) validatedResponse.get(ClaimsConstant.AUTHORIZE_PARTY);
                Map<String, String> userRolesWithScope = (Map<String, String>) validatedResponse.get(ClaimsConstant.APP_SCOPE_WITH_ROLE);

                if (jwtUtil.hasRecruitmentPermission(azpWithRoles)) {
                    log.info("Prepare lists for granted roles and authorities.");

                    List<SimpleGrantedAuthority> grantedRolesAndAuthorities = new ArrayList<>();

                    // Extract roles
                    log.info("Extracting roles and add them as granted roles with ROLE_ prefix...");
                    List<SimpleGrantedAuthority> grantedRoles = new ArrayList<>();
                    for (Map.Entry<String, String> roleWithScope : userRolesWithScope.entrySet()) {
                        if (roleWithScope.getKey().equals(RolePermissionsConstants.APP_SCOPE_CANDIDATE_SERVICE) ||
                                roleWithScope.getKey().equals(RolePermissionsConstants.APP_SCOPE_RECRUITMENT_SERVICE)) {

                            grantedRoles.add(new SimpleGrantedAuthority("ROLE_" + roleWithScope.getValue())); // Prefix required by Spring Security for roles.
                        }
                    }

                    // Extract permissions
                    log.info("Extracting permissions and add them as granted authorities...");
                    List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
                    for (Map.Entry<String, List<String>> azpWithRole : azpWithRoles.entrySet()) {
                        if (azpWithRole.getKey().equals(RolePermissionsConstants.CLIENT_ID_REM_PMS) &&
                                azpWithRole.getValue().contains(RolePermissionsConstants.PERMISSION_LOCATION_SERVICE)) {

                            grantedAuthorities.add(new SimpleGrantedAuthority(RolePermissionsConstants.PERMISSION_LOCATION_SERVICE));
                        }
                    }

                    if (grantedAuthorities.isEmpty()) {
                        log.error("User has no granted authorities.");
                        throw new UnauthorizedException(AuthErrorMessages.NO_PERMISSION_TO_ACCESS_RESOURCE);

                    } else if (grantedRoles.isEmpty()) {
                        log.error("User has no granted roles.");
                        throw new UnauthorizedException(AuthErrorMessages.NO_PERMISSION_TO_ACCESS_RESOURCE);
                    }

                    grantedRolesAndAuthorities.addAll(grantedAuthorities);
                    grantedRolesAndAuthorities.addAll(grantedRoles);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, authHeader.substring(7), grantedRolesAndAuthorities);
                    log.info("Extract user: {}", objectMapper.writeValueAsString(authentication));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } else {
                    log.error("User has no permission to access the resource.");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write(AuthErrorMessages.NO_PERMISSION_TO_ACCESS_RESOURCE);
                    return;
                }
            }

        } else {
            // Instead of throwing UnauthorizedException, just log and allow request to proceed
            logger.warn("No Authorization header found. Skipping JWT validation.");
        }

        logger.info("Starting filter chain...");
        filterChain.doFilter(request, response);
        logger.info("JWTAuthenticationFilter.doFilterInternal() => ended.");
    }
}
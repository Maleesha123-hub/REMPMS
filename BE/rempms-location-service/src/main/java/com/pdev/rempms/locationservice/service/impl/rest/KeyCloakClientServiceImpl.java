package com.pdev.rempms.locationservice.service.impl.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.rempms.locationservice.client.kcs.KeyCloakServiceClient;
import com.pdev.rempms.locationservice.exception.BaseException;
import com.pdev.rempms.locationservice.exception.FeignCustomException;
import com.pdev.rempms.locationservice.exception.UnauthorizedException;
import com.pdev.rempms.locationservice.service.rest.KeyCloakClientService;
import com.pdev.rempms.locationservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @author @maleeshasa
 * @Date 2024/11/16
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KeyCloakClientServiceImpl implements KeyCloakClientService {

    private final KeyCloakServiceClient keyCloakServiceClient;
    private final ObjectMapper objectMapper;

    /**
     * Validates a JWT token by calling the Keycloak service. This method sends the token to
     * a remote service for validation and processes the response to extract relevant data
     * if the token is valid.
     *
     * @param token {@link String} - the JWT token to be validated
     * @return {@link Map<String, Object>} - a map containing the validation details, such as user information, roles, and permissions
     * @author maleeshasa
     */
    @Override
    public Map<String, Object> validateToken(String token) {
        log.info("KeyCloakClientServiceImpl.validateToken() => started.");
        try {
            log.info("Calling keycloak service to validate user token...");
            ResponseEntity<CommonResponse> response = keyCloakServiceClient.validateToken(token);
            if (response.getStatusCode().equals(HttpStatus.OK) &&
                    Objects.requireNonNull(response.getBody()).getData() != null &&
                    response.getBody().getStatus().equals(HttpStatus.ACCEPTED)) {
                log.info("Token is validated.");

                return objectMapper.convertValue(response.getBody().getData(), new TypeReference<>() {
                });

            } else {
                log.error("Unauthorized user.");
                throw new UnauthorizedException("Unauthorized user.");
            }

        } catch (UnauthorizedException e) {
            log.error("Unauthorized user. Error: {}", e.getMessage());
            throw new UnauthorizedException(e.getMessage());

        } catch (FeignCustomException e) {
            log.error("Error occurred while calling user service to validate token. Error: {}", e.getMessage());
            throw new BaseException(500, "Error occurred while calling user service to validate token. Error: " + e.getMessage());
        }
    }
}

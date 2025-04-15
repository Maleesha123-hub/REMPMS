package com.pdev.rempms.locationservice.service.rest;

import java.util.Map;

/**
 * @author @maleeshasa
 * @Date 2024/11/16
 */
public interface KeyCloakClientService {

    /**
     * Validates a JWT token by calling the Keycloak service. This method sends the token to
     * a remote service for validation and processes the response to extract relevant data
     * if the token is valid.
     *
     * @param token {@link String} - the JWT token with Bearer to be validated
     * @return {@link Map<String, Object>} - a map containing the validation details, such as user information, roles, and permissions
     * @author maleeshasa
     */
    Map<String, Object> validateToken(String token);
}

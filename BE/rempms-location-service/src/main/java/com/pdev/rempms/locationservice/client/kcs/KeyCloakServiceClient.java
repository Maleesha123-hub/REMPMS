package com.pdev.rempms.locationservice.client.kcs;

import com.pdev.rempms.locationservice.constants.CommonConstants;
import com.pdev.rempms.locationservice.util.CommonResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author @maleeshasa
 * @Date 2024/11/15
 */
@FeignClient(name = "pdev-keycloak-service")
@Headers("Content-Type: application/json")
public interface KeyCloakServiceClient {

    /**
     * Validates a JWT token by calling the Keycloak service. This method sends the token to
     * a remote service for validation and processes the response to extract relevant data
     * if the token is valid.
     *
     * @param token {@link String} - the JWT token to be validated
     * @return {@link ResponseEntity<CommonResponse>} - containing the validation details, such as user information, roles, and permissions
     * @author maleeshasa
     */
    @GetMapping(value = "/api/kcs/v1/auth/user/token/validate")
    ResponseEntity<CommonResponse> validateToken(@RequestHeader(CommonConstants.AUTHORIZATION) String token);
}

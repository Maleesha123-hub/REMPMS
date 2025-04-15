package com.pdev.rempms.candidateservice.client;

import com.pdev.rempms.candidateservice.util.CommonResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author @Maleesha99
 * @Date 2024/10/20
 */
@FeignClient(name = "rempms-draft-service")
@Headers("Content-Type: application/json")
public interface DraftServiceClient {

    @GetMapping(value = "/api/draft/v2/candidate-common-profile/findByIdCandidate")
    ResponseEntity<CommonResponse> findByIdCandidate(@RequestParam("idCandidate") Integer idCandidate);
}

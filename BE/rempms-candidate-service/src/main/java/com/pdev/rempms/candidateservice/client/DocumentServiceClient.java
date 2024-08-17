package com.pdev.rempms.candidateservice.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author @Maleesha99
 * @Date 2024/02/18
 */
@FeignClient(name = "rempms-document-service")
@Headers("Content-Type: application/json")
public interface DocumentServiceClient {



}

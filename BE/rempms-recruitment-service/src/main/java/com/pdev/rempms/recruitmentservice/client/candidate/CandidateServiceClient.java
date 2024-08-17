package com.pdev.rempms.recruitmentservice.client.candidate;

import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rempms-candidate-service")
@Headers("Content-Type: application/json")
public interface CandidateServiceClient {

    @GetMapping(value = "/api/candidate/v1/industry/getAllActive")
    ResponseEntity<CommonResponse> getActiveIndustries();

    @GetMapping(value = "/api/candidate/v1/industry/getById/{id}")
    ResponseEntity<CommonResponse> getIndustryById(@PathVariable Integer id);

    @GetMapping(value = "/api/candidate/v1/getById")
    ResponseEntity<CommonResponse> getById(@RequestParam("id") Integer idCandidate);
}

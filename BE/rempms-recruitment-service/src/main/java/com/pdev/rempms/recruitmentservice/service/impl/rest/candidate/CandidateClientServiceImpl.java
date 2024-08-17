package com.pdev.rempms.recruitmentservice.service.impl.rest.candidate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.rempms.recruitmentservice.client.candidate.CandidateServiceClient;
import com.pdev.rempms.recruitmentservice.dto.candidate.CandidateDTO;
import com.pdev.rempms.recruitmentservice.dto.candidate.industry.IndustryDTO;
import com.pdev.rempms.recruitmentservice.exception.BaseException;
import com.pdev.rempms.recruitmentservice.exception.FeignCustomException;
import com.pdev.rempms.recruitmentservice.service.rest.candidate.CandidateClientService;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CandidateClientServiceImpl implements CandidateClientService {

    private final CandidateServiceClient candidateServiceClient;
    private final ObjectMapper objectMapper;

    @Override
    public List<IndustryDTO> getActiveIndustries() {

        try {
            ResponseEntity<CommonResponse> response = candidateServiceClient.getActiveIndustries();
            if (response.getStatusCode() == HttpStatus.OK &&
                    Objects.requireNonNull(response.getBody()).getData() != null &&
                    response.getBody().getStatus().equals(HttpStatus.OK)) {
                return objectMapper.convertValue(response.getBody().getData(), new TypeReference<List<IndustryDTO>>() {
                });
            } else {
                return null;
            }
        } catch (FeignCustomException e) {
            throw new BaseException(500, "Error while calling getActiveIndustries() in candidate service, message: " + e.getMessage() + " body: " + e.getBody());
        }
    }

    @Override
    public IndustryDTO getIndustryById(Integer id) {

        try {
            ResponseEntity<CommonResponse> response = candidateServiceClient.getIndustryById(id);
            if (response.getStatusCode() == HttpStatus.OK &&
                    Objects.requireNonNull(response.getBody()).getData() != null &&
                    response.getBody().getStatus().equals(HttpStatus.OK)) {
                return objectMapper.convertValue(response.getBody().getData(), IndustryDTO.class);
            } else {
                return null;
            }
        } catch (FeignCustomException e) {
            throw new BaseException(500, "Error while calling getById() in candidate service, message: " + e.getMessage() + " body: " + e.getBody());
        }
    }

    @Override
    public CandidateDTO getCandidateById(Integer candidateId) {
        try {
            ResponseEntity<CommonResponse> response = candidateServiceClient.getById(candidateId);
            if (response.getStatusCode() == HttpStatus.OK &&
                    Objects.requireNonNull(response.getBody()).getData() != null &&
                    response.getBody().getStatus().equals(HttpStatus.OK)) {
                return objectMapper.convertValue(response.getBody().getData(), CandidateDTO.class);

            } else {
                return null;
            }

        } catch (FeignCustomException e) {
            throw new BaseException(500, "Error while calling getById() in candidate service, message: " + e.getMessage() + " body: " + e.getBody());
        }
    }

}

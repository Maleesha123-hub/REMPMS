package com.pdev.rempms.candidateservice.service.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.rempms.candidateservice.client.DraftServiceClient;
import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileResponseDTO;
import com.pdev.rempms.candidateservice.exception.BaseException;
import com.pdev.rempms.candidateservice.exception.FeignCustomException;
import com.pdev.rempms.candidateservice.service.rest.RestDraftClientService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestDraftClientServiceImpl implements RestDraftClientService {

    private final DraftServiceClient draftServiceClient;
    private final ObjectMapper objectMapper;

    @Override
    public CommonProfileRequestDTO findByIdCandidate(Integer idCandidate) {
        try {
            ResponseEntity<CommonResponse> commonResponse = draftServiceClient.findByIdCandidate(idCandidate);
            if (commonResponse.getStatusCode().equals(HttpStatus.OK)) {
                return objectMapper.convertValue(Objects.requireNonNull(commonResponse.getBody()).getData(), CommonProfileRequestDTO.class);

            } else {
                return null;
            }

        } catch (FeignCustomException e) {
            throw new BaseException(500, "Error occurred while fetching candidate draft details by id from draft service. error: " + e.getMessage());
        }
    }
}

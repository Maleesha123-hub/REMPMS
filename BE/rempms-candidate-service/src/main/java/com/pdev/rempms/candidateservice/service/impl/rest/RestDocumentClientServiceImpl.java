package com.pdev.rempms.candidateservice.service.impl.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.rempms.candidateservice.client.DocumentServiceClient;
import com.pdev.rempms.candidateservice.dto.document.upload.DocumentUploadResponseDTO;
import com.pdev.rempms.candidateservice.enums.FolderType;
import com.pdev.rempms.candidateservice.exception.BaseException;
import com.pdev.rempms.candidateservice.exception.FeignCustomException;
import com.pdev.rempms.candidateservice.service.rest.RestDocumentClientService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RestDocumentClientServiceImpl implements RestDocumentClientService {

    private final DocumentServiceClient documentServiceClient;
    private final ObjectMapper objectMapper;

    @Override
    public List<DocumentUploadResponseDTO> uploadDocuments(FolderType folderType, String refNo,
                                                           String documentType, MultipartFile[] multipartFiles) {
        try {
            ResponseEntity<CommonResponse> response = documentServiceClient.uploadDocuments(folderType, refNo, documentType, multipartFiles);
            if (response.getStatusCode().equals(HttpStatus.OK) &&
                    Objects.requireNonNull(response.getBody()).getData() != null) {
                return objectMapper.convertValue(response.getBody().getData(), new TypeReference<>() {
                });
            } else {
                return null;
            }

        } catch (FeignCustomException e) {
            throw new BaseException(500, "Error occurred while calling document service to upload documents. Error: " + e.getMessage());
        }
    }
}

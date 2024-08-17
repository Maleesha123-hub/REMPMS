package com.pdev.rempms.recruitmentservice.service.impl.rest.document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.rempms.recruitmentservice.client.document.DocumentServiceClient;
import com.pdev.rempms.recruitmentservice.constants.enums.FolderType;
import com.pdev.rempms.recruitmentservice.dto.document.upload.DocumentUploadResponseDTO;
import com.pdev.rempms.recruitmentservice.exception.BaseException;
import com.pdev.rempms.recruitmentservice.exception.FeignCustomException;
import com.pdev.rempms.recruitmentservice.service.rest.document.DocumentClientService;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DocumentClientServiceImpl implements DocumentClientService {

    private final DocumentServiceClient documentServiceClient;
    private final ObjectMapper objectMapper;

    @Override
    public List<DocumentUploadResponseDTO> uploadDocuments(FolderType folderType, String refNo,
                                                           String documentType, MultipartFile[] files) {

        try {
            ResponseEntity<CommonResponse> response = documentServiceClient.uploadDocuments(folderType, refNo, documentType, files);
            if (response.getStatusCode().equals(HttpStatus.OK) &&
                    Objects.requireNonNull(response.getBody()).getData() != null) {
                return objectMapper.convertValue(response.getBody().getData(), new TypeReference<>() {
                });
            } else {
                return null;
            }

        } catch (FeignCustomException e) {
            throw new BaseException(500, "Exception occurred while fetching document upload response from document service. body:" + e.getBody() + " message: " + e.getMessage());
        }
    }

    @Override
    public CommonResponse deleteFiles(List<String> pathUrls) {
        try {
            ResponseEntity<CommonResponse> response = documentServiceClient.deleteFiles(pathUrls);
            if (response.getStatusCode().equals(HttpStatus.OK) &&
                    Objects.requireNonNull(response.getBody()).getData() != null) {
                return response.getBody();

            } else {
                return null;
            }

        } catch (FeignCustomException e) {
            throw new BaseException(500, "Exception occurred while deleting uploaded files response from document service. body:" + e.getBody() + " message: " + e.getMessage());
        }
    }

}

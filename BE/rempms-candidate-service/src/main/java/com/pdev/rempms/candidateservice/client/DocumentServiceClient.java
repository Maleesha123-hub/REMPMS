package com.pdev.rempms.candidateservice.client;

import com.pdev.rempms.candidateservice.enums.FolderType;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author @Maleesha99
 * @Date 2024/02/18
 */
@FeignClient(name = "rempms-document-service")
public interface DocumentServiceClient {

    @PostMapping(value = "/api/document/v1/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<CommonResponse> uploadDocuments(@RequestParam(value = "folderType") FolderType folderType,
                                                   @RequestParam(value = "refNo") String refNo,
                                                   @RequestParam(value = "documentType") String documentType,
                                                   @RequestPart(value = "documents") MultipartFile[] multipartFiles);
}

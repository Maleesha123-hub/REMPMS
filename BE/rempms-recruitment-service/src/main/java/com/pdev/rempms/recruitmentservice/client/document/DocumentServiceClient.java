package com.pdev.rempms.recruitmentservice.client.document;

import com.pdev.rempms.recruitmentservice.constants.enums.FolderType;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "rempms-document-service")
@Headers("Content-Type: application/json")
public interface DocumentServiceClient {

    @PostMapping(value = "/api/document/v1/upload", consumes = "multipart/form-data")
    ResponseEntity<CommonResponse> uploadDocuments(@RequestParam(value = "folderType") FolderType folderType,
                                                   @RequestParam(value = "refNo") String refNo,
                                                   @RequestParam(value = "documentType") String documentType,
                                                   @RequestPart(value = "documents") MultipartFile[] files
    );

    @PostMapping(value = "/api/document/v1/deleteFiles")
    ResponseEntity<CommonResponse> deleteFiles(@RequestParam(name = "urls") List<String> urls);
}

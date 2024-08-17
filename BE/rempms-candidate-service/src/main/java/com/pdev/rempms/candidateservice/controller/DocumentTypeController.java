package com.pdev.rempms.candidateservice.controller;

import com.pdev.rempms.candidateservice.service.DocumentTypeService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/candidate/v1/document-type")
public class DocumentTypeController {

    private final DocumentTypeService documentTypeService;

    @GetMapping(value = "getAllActive")
    public ResponseEntity<CommonResponse> getAllActive(){

        return ResponseEntity.ok(documentTypeService.getActiveDocumentTypes());

    }

}

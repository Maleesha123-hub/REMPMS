package com.pdev.rempms.candidateservice.service.rest;

import com.pdev.rempms.candidateservice.dto.document.upload.DocumentUploadResponseDTO;
import com.pdev.rempms.candidateservice.enums.FolderType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
public interface RestDocumentClientService {

    List<DocumentUploadResponseDTO> uploadDocuments(FolderType folderType,
                                                    String refNo,
                                                    String documentType,
                                                    MultipartFile[] multipartFiles);
}

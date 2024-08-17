package com.pdev.rempms.recruitmentservice.service.rest.document;

import com.pdev.rempms.recruitmentservice.constants.enums.FolderType;
import com.pdev.rempms.recruitmentservice.dto.document.upload.DocumentUploadResponseDTO;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentClientService {

    List<DocumentUploadResponseDTO> uploadDocuments(FolderType folderType,
                                                    String refNo,
                                                    String documentType,
                                                    MultipartFile[] multipartFiles);

    CommonResponse deleteFiles(List<String> pathUrls);
}

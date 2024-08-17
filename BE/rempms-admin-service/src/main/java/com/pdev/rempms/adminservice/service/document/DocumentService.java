package com.pdev.rempms.adminservice.service.document;

import com.pdev.rempms.adminservice.constants.enums.DocumentType;
import com.pdev.rempms.adminservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author maleeshasa
 * @Date 23/12/2023
 */
public interface DocumentService {

    /**
     * upload the file in to the file location
     *
     * @param files   - Communication information saving or updating details
     * @param docType - uploaded document type
     * @param id
     * @return - success or fail status of document upload
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> fileUpload(MultipartFile[] files, String id, DocumentType docType);

    /**
     * get document path by user account id, doc type, and image name
     *
     * @param id
     * @param docType
     * @param imageName
     * @return - document path
     * @author maleeshasa
     */
    String getDocumentPathByUserAccountIdTypeAndImageName(Long id, DocumentType docType, String imageName);
}

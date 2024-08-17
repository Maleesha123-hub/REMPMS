package com.pdev.rempms.documentservice.service;


import com.pdev.rempms.documentservice.dto.download.ImageDownloadRequestDTO;
import com.pdev.rempms.documentservice.dto.generate.PDFRequest;
import com.pdev.rempms.documentservice.enums.FolderType;
import com.pdev.rempms.documentservice.util.CommonResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
public interface DocumentService {

    /**
     * This method is allowed to upload documents
     *
     * @param folderType     {@link FolderType} - ex: CANDIDATE/RECRUITMENT/EMPLOYEE
     * @param refNo          {@link String} - ex: candidate no/job ref no/employee no
     * @param documentType   {@link String} - ex: NIC/Certificates/CV
     * @param multipartFiles {@link MultipartFile} - files
     * @return {@link CommonResponse} - uploaded document response: doc name, path url
     * @author @Maleesha99
     */
    CommonResponse uploadDocuments(FolderType folderType, String refNo, String documentType, MultipartFile[] multipartFiles);

    /**
     * This method is allowed to download uploaded file.
     *
     * @param request {@link ImageDownloadRequestDTO} - content type (ex: JPEG, PNG, PDF) and path url
     * @return {@link byte[]} - downloaded single file
     * @author @Maleesha99
     */
    byte[] downloadSingleFile(ImageDownloadRequestDTO request);

    /**
     * This method is allowed to download a zip file including uploaded files.
     *
     * @param requests {@link List<ImageDownloadRequestDTO>} - content type (ex: JPEG, PNG, PDF) and path url
     * @return {@link byte[]} - downloaded zip file
     * @author @Maleesha99
     */
    byte[] downloadZipFile(List<ImageDownloadRequestDTO> requests);

    /**
     * This method is allowed to generate pdf for specific data set and a predefined template
     *
     * @return {@link ByteArrayResource} - generated pdf document response ByteArrayResource
     * @author @Maleesha99
     */
    ByteArrayResource generatePdfDocument(PDFRequest pdfRequest);

    /**
     * This method is allowed to delete uploaded files by path urls
     *
     * @param urls {@link List<String>} - uploaded path urls
     * @return {@link CommonResponse} - deleted files path urls
     * @author @Maleesha99
     */
    CommonResponse deleteFiles(List<String> urls);
}

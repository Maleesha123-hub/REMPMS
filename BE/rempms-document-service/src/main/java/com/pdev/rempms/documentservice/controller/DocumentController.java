package com.pdev.rempms.documentservice.controller;

import com.pdev.rempms.documentservice.dto.download.ImageDownloadRequestDTO;
import com.pdev.rempms.documentservice.dto.generate.PDFRequest;
import com.pdev.rempms.documentservice.enums.ContentType;
import com.pdev.rempms.documentservice.enums.FolderType;
import com.pdev.rempms.documentservice.service.DocumentService;
import com.pdev.rempms.documentservice.util.CommonResponse;
import com.pdev.rempms.documentservice.util.DocumentFileName;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/document/v1")
public class DocumentController {

    private final DocumentService documentService;

    /**
     * This method is allowed to upload documents
     *
     * @param folderType     {@link FolderType} - ex: CANDIDATE/RECRUITMENT/EMPLOYEE
     * @param refNo          {@link String} - ex: candidate no/job ref no/employee no
     * @param documentType   {@link String} - ex: NIC/Certificates/CV
     * @param multipartFiles {@link MultipartFile} - files
     * @return {@link ResponseEntity<CommonResponse>} - uploaded document response: doc name, path url
     * @author @Maleesha99
     */
    @PostMapping(value = "/upload")
    public ResponseEntity<CommonResponse> uploadDocuments(@RequestParam(value = "folderType") FolderType folderType,
                                                          @RequestParam(value = "refNo") String refNo,
                                                          @RequestParam(value = "documentType") String documentType,
                                                          @RequestPart(value = "documents") MultipartFile[] multipartFiles) {
        log.info("DocumentController.uploadDocuments() => started.");
        CommonResponse response = documentService.uploadDocuments(folderType, refNo, documentType, multipartFiles);
        log.info("DocumentController.uploadDocuments() => ended.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * This method is allowed to delete uploaded files by path urls
     *
     * @param urls {@link List<String>} - uploaded path urls
     * @return {@link ResponseEntity<CommonResponse>} - deleted files path urls
     * @author @Maleesha99
     */
    @PostMapping(value = "/deleteFiles")
    public ResponseEntity<CommonResponse> deleteFiles(@RequestParam(name = "urls") List<String> urls) {
        log.info("DocumentController.deleteFiles() => started.");
        return ResponseEntity.ok(documentService.deleteFiles(urls));
    }

    /**
     * This method is allowed to generate pdf for specific data set and a predefined template
     *
     * @return {@link ResponseEntity<ByteArrayResource>} - generated pdf document response ByteArrayResource
     * @author @Maleesha99
     */
    @PostMapping(value = "/generatePdf")
    public ResponseEntity<ByteArrayResource> generatePdfDocument(@RequestBody PDFRequest pdfRequest) {
        log.info("DocumentController.generatePdfDocument() => started.");

        ByteArrayResource byteArrayResource = documentService.generatePdfDocument(pdfRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename=" + pdfRequest.getDocumentName().concat(".pdf"));
        httpHeaders.setContentType(new MediaType("application", pdfRequest.getDocumentName().concat(".pdf")));

        log.info("DocumentController.generatePdfDocument() => ended.");
        return new ResponseEntity<>(byteArrayResource, httpHeaders, HttpStatus.OK);
    }

    /**
     * This method is allowed to download uploaded files.
     * If sent a single path url >> return a single file in a byte array.
     * If sent multiple path urls >> return a zip file including compressed files in a byte array.
     *
     * @param requests {@link List<ImageDownloadRequestDTO>} - content type (ex: JPEG, PNG, PDF) and path urls
     * @return {@link ResponseEntity<?>} - downloaded files - zip or single file
     * @author @Maleesha99
     */
    @PostMapping(value = "/downloader")
    public ResponseEntity<?> fileDownloader(@RequestPart(value = "requests") List<ImageDownloadRequestDTO> requests) {
        log.info("DocumentController.fileDownloader() => started.");

        if (requests.size() == 1) {
            log.info("Single file...");
            // Downloading single file by given url path
            byte[] fileContent = documentService.downloadSingleFile(requests.getFirst());

            // Set response headers
            String fileName = DocumentFileName.getFileName(requests.getFirst().getDocumentPath()); // ex: abc.jpg
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentDispositionFormData("inline", fileName);
            httpHeaders.setContentType(MediaType.parseMediaType(ContentType.valueOf(requests.getFirst().getContentType()).getName()));
            httpHeaders.setContentLength(fileContent.length);
            log.info("Single file downloaded success.");
            return new ResponseEntity<>(fileContent, httpHeaders, HttpStatus.OK);

        } else if (requests.size() > 1) {
            log.info("Multiple files...");
            // Downloading zip file including compressed files by given url paths
            byte[] zipContent = documentService.downloadZipFile(requests);

            // Set response headers
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentDispositionFormData("attachment", "files.zip");
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentLength(zipContent.length);
            log.info("Zip file downloaded success.");
            return new ResponseEntity<>(zipContent, httpHeaders, HttpStatus.OK);

        } else {
            // Handle the case where the requests list is empty
            log.warn("DocumentController.downloader() => no requests provided.");
            return ResponseEntity.badRequest().body("No download requests provided.");
        }
    }

    //TODO
//    Need to implement a common api to generate a document according to a predefined template
//    and save that generated document
//    and return doc name and url path
    // modify previously created api to achieve this

}

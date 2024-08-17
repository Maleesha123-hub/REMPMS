package com.pdev.rempms.adminservice.controller;

import com.pdev.rempms.adminservice.constants.enums.DocumentType;
import com.pdev.rempms.adminservice.service.document.DocumentService;
import com.pdev.rempms.adminservice.util.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * @author maleeshasa
 * @Date 18/12/2023
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/document")
public class DocumentController {

    private final DocumentService documentService;

    /**
     * upload the file in to the file location
     *
     * @param files   - Communication information saving or updating details
     * @param docType - uploaded document type
     * @param id
     * @return - success or fail status of document upload
     * @author maleeshasa
     */
    @PostMapping(value = "/upload")
    public ResponseEntity<CommonResponse> upload(@RequestParam("file") MultipartFile[] files,
                                                 @RequestParam("id") String id,
                                                 @RequestParam("docType") DocumentType docType) {
        return documentService.fileUpload(files, id, docType);
    }

    /**
     * get document path by user account id, doc type, and image name
     *
     * @param id
     * @param docType
     * @param imageName
     * @return - document path
     * @author maleeshasa
     */
    @GetMapping("/downloader/{id}/{imageName}/{docType}")
    public void downloader(HttpServletResponse response,
                           @PathVariable Long id,
                           @PathVariable String imageName,
                           @PathVariable String docType) {
        try {
            String documentPath = documentService.getDocumentPathByUserAccountIdTypeAndImageName(id, DocumentType.valueOf(docType), imageName);
            if (documentPath != null) {
                String pathToImage = "inline;filename=";
                byte[] image = Files.readAllBytes(new File(documentPath).toPath());
                response.setHeader("Content-Disposition", pathToImage.concat(documentPath));
                OutputStream out = response.getOutputStream();
                response.setContentType(docType);
                IOUtils.copy(new ByteArrayInputStream(image), out);
                out.flush();
                out.close();
            }
        } catch (Exception ex) {
            log.info("DocumentController -> downloader() => {} " + ex);
        }
    }
}

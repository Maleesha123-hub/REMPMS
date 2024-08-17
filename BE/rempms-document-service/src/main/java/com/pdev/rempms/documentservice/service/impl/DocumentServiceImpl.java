package com.pdev.rempms.documentservice.service.impl;

import com.pdev.rempms.documentservice.dto.download.ImageDownloadRequestDTO;
import com.pdev.rempms.documentservice.dto.generate.PDFRequest;
import com.pdev.rempms.documentservice.dto.upload.DocumentUploadResponseDTO;
import com.pdev.rempms.documentservice.enums.DocumentTemplateType;
import com.pdev.rempms.documentservice.enums.FolderType;
import com.pdev.rempms.documentservice.enums.SubFolderType;
import com.pdev.rempms.documentservice.exception.BaseException;
import com.pdev.rempms.documentservice.exception.RecordNotFoundException;
import com.pdev.rempms.documentservice.service.DocumentService;
import com.pdev.rempms.documentservice.service.validations.ContentTypeValidation;
import com.pdev.rempms.documentservice.util.CommonProperties;
import com.pdev.rempms.documentservice.util.CommonResponse;
import com.pdev.rempms.documentservice.util.DocumentFileName;
import com.pdev.rempms.documentservice.util.PdfGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final CommonProperties commonProperties;
    private final PdfGenerator pdfGenerator;
    private final HttpServletResponse res;
    private final HttpServletRequest req;
    private final TemplateEngine templateEngine;
    private final ContentTypeValidation contentTypeValidation;

    /**
     * This method is allowed to upload documents
     *
     * @param folderType   {@link FolderType} - ex: CANDIDATE/RECRUITMENT/EMPLOYEE
     * @param refNo        {@link String} - ex: candidate no/job ref no/employee no
     * @param documentType {@link String} - ex: NIC/Certificates/CV
     * @param documents    {@link MultipartFile[]} - files
     * @return {@link CommonResponse} - uploaded document response: doc name, path url
     * @author @Maleesha99
     */
    @Override
    public CommonResponse uploadDocuments(FolderType folderType, String refNo, String documentType, MultipartFile[] documents) {
        log.info("DocumentServiceImpl.uploadDocuments() => started.");

        CommonResponse response = new CommonResponse();

        List<DocumentUploadResponseDTO> documentUploadResponses = saveDocuments(
                SubFolderType.UPLOADED,
                folderType,
                refNo,
                documentType,
                documents);

        response.setData(documentUploadResponses);
        response.setStatus(HttpStatus.OK);
        response.setMessage("Document upload success.");

        log.info("DocumentServiceImpl.uploadDocuments() => ended.");
        return response;
    }

    /**
     * This method is allowed to download uploaded file.
     *
     * @param request {@link ImageDownloadRequestDTO} - content type (ex: JPEG, PNG, PDF) and path url
     * @return {@link byte[]} - downloaded single file
     * @author @Maleesha99
     */
    @Override
    public byte[] downloadSingleFile(ImageDownloadRequestDTO request) {
        log.info("DocumentServiceImpl.downloadSingleFile() = started.");

        if (!contentTypeValidation.validateContentType(request.getContentType())) {
            log.warn("DocumentServiceImpl.downloadSingleFile() => Invalid content type.");
            throw new BaseException(500, "Content type is invalid.");
        }

        try {
            // Read file bytes
            byte[] file = Files.readAllBytes(new File(commonProperties.getBaseFilePath() + request.getDocumentPath()).toPath());

            // file response
            return file;

        } catch (NoSuchFileException ex) {
            log.warn("DocumentServiceImpl.downloadSingleFile() => No such file.");
            throw new BaseException(500, "File is not exists.");

        } catch (Exception ex) {
            log.warn("DocumentServiceImpl.downloadSingleFile() => Error.");
            throw new BaseException(500, "Document download fail.");
        }
    }

    /**
     * This method is allowed to download a zip file including uploaded files.
     *
     * @param requests {@link List<ImageDownloadRequestDTO>} - content type (ex: JPEG, PNG, PDF) and path url
     * @return {@link byte[]} - downloaded zip file
     * @author @Maleesha99
     */
    @Override
    public byte[] downloadZipFile(List<ImageDownloadRequestDTO> requests) {
        log.info("DocumentServiceImpl.downloadZipFile() = started.");

        ByteArrayOutputStream bouts = new ByteArrayOutputStream();

        // Compress files into a single byte array
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(bouts)) {

            int i = 1;
            for (ImageDownloadRequestDTO request : requests) {
                // Setting file path with base file path
                Path path = Paths.get(commonProperties.getBaseFilePath(), request.getDocumentPath());
                // Setting file names for each file
                String fileName = (i) + "_" + DocumentFileName.getFileName(request.getDocumentPath());
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                StreamUtils.copy(Files.newInputStream(path), zipOutputStream);
                zipOutputStream.closeEntry();
                ++i;
            }
            zipOutputStream.finish();

            return bouts.toByteArray();

        } catch (IOException e) {
            log.warn("Files zipping failed.");
            throw new RuntimeException("Files zipping failed.");
        }
    }

    /**
     * This method is allowed to save generated or uploaded documents in a file location and
     * Then return the uploaded or generated documents saved locations
     *
     * @param subFolderType {@link SubFolderType} - ex: GENERATED, UPLOADED
     * @param folderType    {@link FolderType} - ex: CANDIDATE, RECRUITMENT, EMPLOYEE
     * @param refNo         {@link String} - ex: CAN-2024000013, REC-2024000013, EMP-2024000013
     * @param documentType  {@link String} - ex: NIC, Cv
     * @param documents     {@link MultipartFile[]}
     * @return {@link List<DocumentUploadResponseDTO>} - Saved file's doc name and path url
     * @author @Maleesha99
     */
    private List<DocumentUploadResponseDTO> saveDocuments(SubFolderType subFolderType,
                                                          FolderType folderType,
                                                          String refNo,
                                                          String documentType,
                                                          MultipartFile[] documents) {
        log.info("DocumentServiceImpl.saveDocuments() => started.");
        List<DocumentUploadResponseDTO> documentUploadResponses = new ArrayList<>();
        Path filePath = null;

        if (subFolderType.equals(SubFolderType.UPLOADED)) {
            log.info("Documents are uploaded type.");

            filePath = Paths.get(
                    commonProperties.getBaseFilePath(),
                    String.valueOf(folderType),
                    refNo,
                    documentType,
                    "uploaded_documents"
            );
        } else if (subFolderType.equals(SubFolderType.GENERATED)) {
            log.info("Documents are generated type.");

            filePath = Paths.get(
                    commonProperties.getBaseFilePath(),
                    String.valueOf(folderType),
                    refNo,
                    documentType,
                    "generated_documents"
            );
        }

        File folder = new File(filePath.toString());

        if (!folder.exists()) {
            log.info("Document saving folder is exists.");
            // Creating a directory and any necessary parent directories
            boolean mkdirs = folder.mkdirs();
            log.info(String.valueOf(mkdirs));
        }

        // Save file list
        for (MultipartFile document : documents) {

            try {
                String originalFileName = Objects.requireNonNull(document.getOriginalFilename()).concat(String.valueOf(new Date().getTime()));
                File file = new File(folder.getPath(), originalFileName);
                document.transferTo(file);

                // Get the original file path without the base path
                String originalFilePath = filePath.toString()
                        .concat(File.separator)
                        .concat(originalFileName);

                // Get the saved file path in server location with base path
                String savedFilePath = originalFilePath.replace(commonProperties.getBaseFilePath(), "");

                DocumentUploadResponseDTO documentUploadResponseDTO = new DocumentUploadResponseDTO();
                documentUploadResponseDTO.setDocName(originalFileName);
                documentUploadResponseDTO.setPathUrl(savedFilePath);
                documentUploadResponses.add(documentUploadResponseDTO);

            } catch (IOException e) {
                log.warn("Error occurred during document upload.");
                throw new BaseException(500, "Error occurred during document upload.");
            }
        }
        log.info("DocumentServiceImpl.saveDocuments() => ended.");
        return documentUploadResponses;
    }

    /**
     * This method is allowed to generate pdf for specific data set and a predefined template
     *
     * @return {@link ByteArrayResource} - generated pdf document response ByteArrayResource
     * @author @Maleesha99
     */
    @Override
    public ByteArrayResource generatePdfDocument(PDFRequest pdfRequest) {
        log.info("DocumentServiceImpl.generatePdfDocument() => started.");

        ByteArrayOutputStream byteArrayOutputStream;
        if (Arrays.stream(DocumentTemplateType.values()).anyMatch(type -> type.getName()
                .equalsIgnoreCase(pdfRequest.getDocumentName()))) {

            // Pdf is generating according to the data
            byteArrayOutputStream = pdfGenerator.pdfGenerator(req, res, pdfRequest.getData(),
                    pdfRequest.getDocumentName(), templateEngine, commonProperties.getFont());

        } else {
            log.warn("Can not find the html template.");
            throw new RecordNotFoundException("Can not find the html template for " + pdfRequest.getDocumentName());
        }

        ByteArrayResource byteArrayResource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
        log.info("DocumentServiceImpl.generatePdfDocument() => ended.");
        return byteArrayResource;
    }

    /**
     * This method is allowed to delete uploaded files by path urls
     *
     * @param urls {@link List<String>} - uploaded path urls
     * @return {@link CommonResponse} - deleted files path urls
     * @author @Maleesha99
     */
    @Override
    public CommonResponse deleteFiles(List<String> urls) {
        log.info("DocumentServiceImpl.deleteFiles() => started.");
        CommonResponse commonResponse = new CommonResponse();
        List<String> deletedUrls = new ArrayList<>();
        for (String url : urls) {
            File file = new File(commonProperties.getBaseFilePath() + url);
            if (file.exists()) {
                try {
                    file.delete();
                    deletedUrls.add(url);
                    log.info("DocumentServiceImpl.deleteFiles() => deleted. url: {}", url);

                } catch (Exception e) {
                    log.warn("Error occurred during document deletion. url: {}", url);
                    commonResponse.setMessage("Error occurred during document deletion.");
                    commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                    commonResponse.setData(null);
                }

            } else {
                log.warn("Can not find the file. url: {}", url);
            }
        }
        commonResponse.setData(deletedUrls);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage("Files deletion success response.");
        log.info("DocumentServiceImpl.deleteFiles() => ended.");
        return commonResponse;
    }

}

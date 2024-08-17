package com.pdev.rempms.adminservice.service.impl.document;

import com.pdev.rempms.adminservice.constants.enums.CommonStatus;
import com.pdev.rempms.adminservice.constants.enums.DocumentType;
import com.pdev.rempms.adminservice.constants.validation.DocumentValidationMessages;
import com.pdev.rempms.adminservice.repository.UserAccountRepository;
import com.pdev.rempms.adminservice.service.document.DocumentService;
import com.pdev.rempms.adminservice.service.userAccount.UserAccountService;
import com.pdev.rempms.adminservice.util.CommonProperties;
import com.pdev.rempms.adminservice.util.CommonResponse;
import com.pdev.rempms.adminservice.util.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author maleeshasa
 * @Date 23/12/2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final CommonProperties commonProperties;
    private final UserAccountService userAccountService;
    private final UserAccountRepository userAccountRepository;

    /**
     * upload the file in to the file location
     *
     * @param files   - uploaded multipart file
     * @param docType - uploaded document type
     * @param id
     * @return - success or fail status of document upload
     * @author maleeshasa
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEntity<CommonResponse> fileUpload(MultipartFile[] files, String id, DocumentType docType) {
        log.info("DocumentServiceImpl -> fileUpload() => started");
        CommonResponse commonResponse = new CommonResponse();
        try {
            String validation = validateFileUpload(files, id, docType);
            if (!CommonValidation.stringNullValidation(validation)) {
                log.info("DocumentServiceImpl -> fileUpload() => Validation error messages available.");
                commonResponse.setMessage(validation);
                commonResponse.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                return new ResponseEntity<>(commonResponse, HttpStatus.OK);
            }
            String savedFileName;
            switch (docType) {
                case USER_IMG:
                    savedFileName = saveUserImage(files);
                    commonResponse.setData(new ArrayList<>(Collections.singletonList(savedFileName)));
                    break;
                case POSTS_IMG:
                    savedFileName = saveJobPostImage(files);
                    commonResponse.setData(new ArrayList<>(Collections.singletonList(savedFileName)));
                    break;
                case CV_DOCUMENT:
                    savedFileName = saveCvDocument(files);
                    commonResponse.setData(new ArrayList<>(Collections.singletonList(savedFileName)));
                    break;
                default:
                    log.info("DocumentServiceImpl -> fileUpload() => Unexpected document type!");
                    commonResponse.setMessage("Unexpected document type.");
            }
        } catch (Exception ex) {
            log.error("DocumentServiceImpl -> fileUpload() => File upload failed!" + ex);
            commonResponse.setMessage("File upload failed.");
        }
        commonResponse.setStatus(HttpStatus.OK);
        log.info("DocumentServiceImpl -> fileUpload() => ended");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * validate the uploaded file by extension and file size
     *
     * @param files   - uploaded multipart file
     * @param docType - uploaded document type
     * @param id
     * @return - validations of uploaded file
     * @author maleeshasa
     */
    private String validateFileUpload(MultipartFile[] files, String id, DocumentType docType) {
        log.info("DocumentServiceImpl -> validateFileUpload() => started.");
        String validation = null;
        Long maxSize = null;
        if (docType.getValue().equalsIgnoreCase(DocumentType.USER_IMG.getValue()) ||
                docType.getValue().equalsIgnoreCase(DocumentType.POSTS_IMG.getValue()) ||
                docType.getValue().equalsIgnoreCase(DocumentType.CV_DOCUMENT.getValue())) {

            maxSize = switch (docType) {
                case POSTS_IMG, USER_IMG -> Long.parseLong(commonProperties.getMaxImageSize());
                case CV_DOCUMENT -> Long.parseLong(commonProperties.getMaxDocumentSize());
                default -> maxSize;
            };

            for (MultipartFile file : files) {
                String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
                long fileSize = file.getSize();
                // check is file type valid or invalid
                if (!CommonValidation.stringNullValidation(extension)) {
                    log.info("DocumentServiceImpl -> validateFileUpload() => Check validity of extension.");
                    switch (docType) {
                        case POSTS_IMG, USER_IMG:
                            if (!isValidImageExtension(extension)) {
                                validation = DocumentValidationMessages.INVALID_IMAGE_TYPE;
                            }
                            break;
                        case CV_DOCUMENT:
                            if (!isValidDocExtension(extension)) {
                                validation = DocumentValidationMessages.INVALID_DOC_TYPE;
                            }
                            break;
                        default:
                            validation = DocumentValidationMessages.INVALID_TYPE;
                    }
                }

                // check is file size grater than max size
                if (fileSize > maxSize) {
                    log.info("DocumentServiceImpl -> validateFileUpload() => Check validity of file size.");
                    validation = switch (docType) {
                        case POSTS_IMG, USER_IMG -> DocumentValidationMessages.LARGE_IMAGE;
                        case CV_DOCUMENT -> DocumentValidationMessages.LARGE_DOCUMENT;
                        default -> DocumentValidationMessages.INVALID_TYPE;
                    };
                }
            }

        } else {
            log.info("DocumentServiceImpl -> validateFileUpload() => Unexpected document type!");
        }
        log.info("DocumentServiceImpl -> validateFileUpload() => ended.");
        return validation;
    }

    /**
     * validate the extension of image file
     *
     * @param extension - extension of uploaded image file
     * @return - validity of the file extension
     * @author maleeshasa
     */
    private boolean isValidImageExtension(String extension) {
        log.info("DocumentServiceImpl -> isValidImageExtension() => started.");
        List<String> imageValidExtensions = Arrays.asList("jpg", "jpeg", "png");
        if (imageValidExtensions.contains(extension)) {
            return true;
        }
        log.info("DocumentServiceImpl -> isValidImageExtension() => ended.");
        return false;
    }

    /**
     * validate the extension of document file
     *
     * @param extension - extension of uploaded document file
     * @return - validity of the file extension
     * @author maleeshasa
     */
    private boolean isValidDocExtension(String extension) {
        log.info("DocumentServiceImpl -> isValidDocExtension() => started.");
        List<String> docValidExtensions = Arrays.asList("pdf");
        if (docValidExtensions.contains(extension)) {
            return true;
        }
        log.info("DocumentServiceImpl -> isValidDocExtension() => ended.");
        return false;
    }

    /**
     * get the extension of uploaded file
     *
     * @param filename - uploaded file name
     * @return - file extension
     * @author maleeshasa
     */
    private String getFileExtension(String filename) {
        log.info("DocumentServiceImpl -> getFileExtension() => started.");
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return filename.substring(lastDotIndex + 1).toLowerCase();
        }
        log.info("DocumentServiceImpl -> getFileExtension() => ended.");
        return "";
    }

    /**
     * save uploaded user account image
     *
     * @param files - uploaded multipart file
     * @return - image name
     * @author maleeshasa
     */
    private String saveUserImage(MultipartFile[] files) throws IOException {
        log.info("DocumentServiceImpl -> saveUserImage() => started.");
        String imageName = files[0].getOriginalFilename().concat("_").concat(String.valueOf(new Date().getTime()));
        String filePath = commonProperties.getUserImageLocation().concat(File.separator).concat(imageName);
        files[0].transferTo(new File(filePath));
        log.info("DocumentServiceImpl -> saveUserImage() => ended.");
        return imageName;
    }

    /**
     * save uploaded job post image
     *
     * @param files - uploaded multipart file
     * @return - image name
     * @author maleeshasa
     */
    private String saveJobPostImage(MultipartFile[] files) throws IOException {
        log.info("DocumentServiceImpl -> saveJobPostImage() => started.");
        String imageName = files[0].getOriginalFilename().concat("_").concat(String.valueOf(new Date().getTime()));
        String filePath = commonProperties.getJobPostImageLocation().concat(File.separator).concat(imageName);
        files[0].transferTo(new File(filePath));
        log.info("DocumentServiceImpl -> saveJobPostImage() => ended.");
        return imageName;
    }

    /**
     * save uploaded cv document
     *
     * @param files - uploaded multipart file
     * @return - document name
     * @author maleeshasa
     */
    private String saveCvDocument(MultipartFile[] files) throws IOException {
        log.info("DocumentServiceImpl -> saveCvDocument() => started.");
        String docName = files[0].getOriginalFilename().concat("_").concat(String.valueOf(new Date().getTime()));
        String filePath = commonProperties.getCvLocation().concat(File.separator).concat(docName);
        files[0].transferTo(new File(filePath));
        log.info("DocumentServiceImpl -> saveCvDocument() => ended.");
        return docName;
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
    @Override
    public String getDocumentPathByUserAccountIdTypeAndImageName(Long id, DocumentType docType, String imageName) {
        log.info("DocumentServiceImpl -> getDocumentPathByUserAccountIdTypeAndImageName() => started.");
        String fileName;
        String filePath;
        switch (docType) {
            case USER_IMG:
                fileName = userAccountRepository.findByIdAndCommonStatus(id, CommonStatus.ACTIVE.getValue()).getUserImage();
                filePath = (fileName == null) ? null : commonProperties.getUserImageLocation().concat(File.separator).concat(fileName);
                break;
            default:
                log.info("DocumentServiceImpl -> getDocumentPathByUserAccountIdTypeAndImageName() => No File Found." + id + "\t :" + docType);
                filePath = null;
        }
        log.info("DocumentServiceImpl -> getDocumentPathByUserAccountIdTypeAndImageName() => ended.");
        return filePath;
    }
}

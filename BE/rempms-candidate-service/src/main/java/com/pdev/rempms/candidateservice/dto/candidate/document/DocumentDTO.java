package com.pdev.rempms.candidateservice.dto.candidate.document;

import com.pdev.rempms.candidateservice.dto.documentType.DocumentTypeDTO;
import jakarta.websocket.Decoder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class DocumentDTO {
    private Integer idDocument;
    private String documentName;
    private String actualFileName;
    private Integer documentTypeId;
    private DocumentTypeDTO documentType;
    private String filePath;
    private Object file;
}

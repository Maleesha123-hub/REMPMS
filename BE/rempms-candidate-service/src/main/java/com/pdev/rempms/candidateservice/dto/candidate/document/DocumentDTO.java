package com.pdev.rempms.candidateservice.dto.candidate.document;

import com.pdev.rempms.candidateservice.dto.documentType.DocumentTypeDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class DocumentDTO {
    private Integer idDocument;
    private String documentName;
    private DocumentTypeDTO documentType;
    private String filePath;
}

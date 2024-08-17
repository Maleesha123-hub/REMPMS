package com.pdev.rempms.candidateservice.mapper.documentType;

import com.pdev.rempms.candidateservice.dto.documentType.DocumentTypeDTO;
import com.pdev.rempms.candidateservice.model.candidate.cvOrCertificate.DocumentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DocumentTypeMapper {

    public DocumentTypeDTO toDto(DocumentTypeDTO dto, DocumentType documentType) {

        dto.setId(documentType.getId());
        dto.setName(documentType.getName());
        dto.setDescription(documentType.getDescription());
        dto.setCommonStatus(documentType.getCommonStatus());

        return dto;

    }

}

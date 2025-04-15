package com.pdev.rempms.candidateservice.mapper.candidate.document;

import com.pdev.rempms.candidateservice.dto.candidate.document.DocumentDTO;
import com.pdev.rempms.candidateservice.dto.document.upload.DocumentUploadResponseDTO;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.cvOrCertificate.Document;
import com.pdev.rempms.candidateservice.model.candidate.cvOrCertificate.DocumentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DocumentMapper {

    public Document toEntity(Document document,
                             DocumentUploadResponseDTO dto,
                             DocumentType type,
                             Candidate candidate) {
        log.info("DocumentMapper -> toEntity() => started!");

        document.setDocumentName(dto.getDocName());
        document.setDocumentType(document.getDocumentType());
        document.setCandidate(candidate);
        document.setFilePath(dto.getPathUrl());
        document.setDocumentType(type);
        document.setActive(true);

        log.info("DocumentMapper -> toEntity() => ended!");
        return document;
    }

    public DocumentDTO toDto(DocumentDTO dto,
                             Document document) {
        log.info("DocumentMapper -> toDto() => started!");

        dto.setIdDocument(document.getId());
        dto.setDocumentName(document.getDocumentName());
        //dto.setDocumentType(document.getDocumentType());
        dto.setFilePath(document.getFilePath());

        log.info("DocumentMapper -> toDto() => ended!");
        return dto;

    }

}

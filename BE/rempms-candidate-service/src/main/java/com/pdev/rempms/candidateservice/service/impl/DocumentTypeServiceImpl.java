package com.pdev.rempms.candidateservice.service.impl;

import com.pdev.rempms.candidateservice.dto.documentType.DocumentTypeDTO;
import com.pdev.rempms.candidateservice.mapper.documentType.DocumentTypeMapper;
import com.pdev.rempms.candidateservice.model.candidate.cvOrCertificate.DocumentType;
import com.pdev.rempms.candidateservice.repository.DocumentTypeRepository;
import com.pdev.rempms.candidateservice.service.DocumentTypeService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTypeMapper documentTypeMapper;

    @Override
    public CommonResponse getActiveDocumentTypes() {

        CommonResponse response = new CommonResponse();

        List<DocumentType> documentTypes = documentTypeRepository.findAll();

        if (!documentTypes.isEmpty()) {

            List<DocumentTypeDTO> dtoList = documentTypes.stream()
                    .map(documentType -> documentTypeMapper.toDto(new DocumentTypeDTO(), documentType)).toList();

            response.setData(dtoList);
            response.setMessage("Document types are exists.");
            response.setStatus(HttpStatus.OK);

        } else {

            response.setData(null);
            response.setMessage("Document types not exists.");
            response.setStatus(HttpStatus.NOT_FOUND);

        }

        return response;

    }

}

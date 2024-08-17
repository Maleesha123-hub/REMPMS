package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.document.DocumentDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
public interface DocumentService {

    CommonResponse saveUpdate(DocumentDTO dto);

}

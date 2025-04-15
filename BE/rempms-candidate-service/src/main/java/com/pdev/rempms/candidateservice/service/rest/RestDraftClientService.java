package com.pdev.rempms.candidateservice.service.rest;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;

public interface RestDraftClientService {

    CommonProfileRequestDTO findByIdCandidate(Integer idCandidate);
}

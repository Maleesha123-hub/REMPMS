package com.pdev.rempms.recruitmentservice.service.rest.candidate;

import com.pdev.rempms.recruitmentservice.dto.candidate.CandidateDTO;
import com.pdev.rempms.recruitmentservice.dto.candidate.industry.IndustryDTO;

import java.util.List;

public interface CandidateClientService {

    List<IndustryDTO> getActiveIndustries();

    IndustryDTO getIndustryById(Integer id);

    CandidateDTO getCandidateById(Integer candidateId);
}

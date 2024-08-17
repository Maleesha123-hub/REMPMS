package com.pdev.rempms.recruitmentservice.mapper.jobPosition;

import com.pdev.rempms.recruitmentservice.dto.jobPosition.JobPositionRequestDTO;
import com.pdev.rempms.recruitmentservice.dto.jobPosition.JobPositionResponseDTO;
import com.pdev.rempms.recruitmentservice.model.jobPosition.JobPosition;
import com.pdev.rempms.recruitmentservice.service.rest.candidate.CandidateClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobPositionMapper {

    private final CandidateClientService candidateClientService;

    public JobPositionResponseDTO toDto(JobPositionResponseDTO dto, JobPosition jobPosition) {
        dto.setId(jobPosition.getId());
        dto.setName(jobPosition.getPosition());
        dto.setIndustryId(jobPosition.getIndustryId());
        dto.setIndustry(candidateClientService.getIndustryById(jobPosition.getIndustryId()));
        dto.setActive(jobPosition.getActive());
        return dto;
    }

    public JobPosition toEntity(JobPosition jobPosition, JobPositionRequestDTO requestDTO) {
        jobPosition.setId(requestDTO.getId());
        jobPosition.setIndustryId(requestDTO.getIndustryId());
        jobPosition.setPosition(requestDTO.getName());
        jobPosition.setActive(Boolean.TRUE);
        return jobPosition;
    }

}

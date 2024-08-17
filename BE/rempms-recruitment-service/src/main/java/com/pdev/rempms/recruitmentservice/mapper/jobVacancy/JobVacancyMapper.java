package com.pdev.rempms.recruitmentservice.mapper.jobVacancy;

import com.pdev.rempms.recruitmentservice.dto.employer.EmployerDTO;
import com.pdev.rempms.recruitmentservice.dto.document.upload.DocumentUploadResponseDTO;
import com.pdev.rempms.recruitmentservice.dto.jobPosition.JobPositionResponseDTO;
import com.pdev.rempms.recruitmentservice.dto.jobVacancy.JobVacancyRequest;
import com.pdev.rempms.recruitmentservice.dto.jobVacancy.JobVacancyResponse;
import com.pdev.rempms.recruitmentservice.mapper.employer.EmployerMapper;
import com.pdev.rempms.recruitmentservice.mapper.jobPosition.JobPositionMapper;
import com.pdev.rempms.recruitmentservice.model.employer.Employer;
import com.pdev.rempms.recruitmentservice.model.jobPosition.JobPosition;
import com.pdev.rempms.recruitmentservice.model.jobVacancy.JobVacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobVacancyMapper {

    private final JobPositionMapper jobPositionMapper;
    private final EmployerMapper employerMapper;

    public JobVacancy toEntity(JobVacancy jobVacancy, JobVacancyRequest jobVacancyRequest, JobPosition jobPosition,
                               Employer employer, DocumentUploadResponseDTO uploadResponse) {
        jobVacancy.setId(jobVacancyRequest.getId());
        jobVacancy.setDescription(jobVacancyRequest.getDescription());
        jobVacancy.setClosingDate(jobVacancyRequest.getClosingDate());
        jobVacancy.setGovtJob(jobVacancyRequest.isGovtJob());
        jobVacancy.setWalksInInterview(jobVacancyRequest.isWalksInInterview());
        jobVacancy.setPartTime(jobVacancyRequest.isPartTime());
        jobVacancy.setActive(Boolean.TRUE);
        jobVacancy.setPosterUrl(jobVacancyRequest.getPosterUrl());
        jobVacancy.setJobPosition(jobPosition);
        jobVacancy.setEmployer(employer);
        jobVacancy.setPosterUrl(uploadResponse.getPathUrl());
        jobVacancy.setPosterName(uploadResponse.getDocName());
        return jobVacancy;
    }

    public JobVacancyResponse toDto(JobVacancyResponse dto, JobVacancy jobVacancy) {
        if (jobVacancy == null) {
            return null;
        }
        dto.setId(jobVacancy.getId());
        dto.setJobPositionId(jobVacancy.getJobPosition() == null ? null : jobVacancy.getJobPosition().getId());
        dto.setPosterName(jobVacancy.getPosterName());
        dto.setDescription(jobVacancy.getDescription());
        dto.setGovtJob(jobVacancy.isGovtJob());
        dto.setPartTime(jobVacancy.isPartTime());
        dto.setWalksInInterview(jobVacancy.isWalksInInterview());
        dto.setClosingDate(jobVacancy.getClosingDate());
        dto.setPosterUrl(jobVacancy.getPosterUrl());
        dto.setActive(jobVacancy.isActive());
        dto.setJobVacancyRefNo(jobVacancy.getRefNo());
        dto.setJobPosition(jobPositionMapper.toDto(new JobPositionResponseDTO(), jobVacancy.getJobPosition()));
        dto.setEmployer(employerMapper.toDto(new EmployerDTO(), jobVacancy.getEmployer()));
        dto.setOpeningDate(jobVacancy.getAuditData().getCreatedOn().toLocalDate());
        return dto;
    }
}

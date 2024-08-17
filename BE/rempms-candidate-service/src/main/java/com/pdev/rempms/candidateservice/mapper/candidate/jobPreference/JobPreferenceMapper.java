package com.pdev.rempms.candidateservice.mapper.candidate.jobPreference;

import com.pdev.rempms.candidateservice.dto.candidate.industry.IndustryDTO;
import com.pdev.rempms.candidateservice.dto.candidate.jobPreference.JobPreferenceRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.jobPreference.JobPreferenceResponseDTO;
import com.pdev.rempms.candidateservice.dto.jobCategory.JobCategoryDTO;
import com.pdev.rempms.candidateservice.mapper.candidate.industry.IndustryMapper;
import com.pdev.rempms.candidateservice.mapper.jobCategory.JobCategoryMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.jobPreference.JobPreference;
import com.pdev.rempms.candidateservice.service.rest.RestCommunicationInfoClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JobPreferenceMapper {

    private final RestCommunicationInfoClientService restCommunicationInfoClientService;
    private final JobCategoryMapper jobCategoryMapper;
    private final IndustryMapper industryMapper;

    /**
     * This method is allowed to convert model to dto
     *
     * @param jobPreference {@link JobPreference} - entity
     * @return {@link JobPreferenceRequestDTO}
     * @author @maleeshasa
     */
    public JobPreferenceResponseDTO modelToDto(JobPreferenceResponseDTO dto, JobPreference jobPreference) {

        dto.setIdJobPreference(jobPreference.getId());
        dto.setPreference(jobPreference.getPreference());
        dto.setRemark(jobPreference.getRemark());

        // Job category
        dto.setIdJobCategory(jobPreference.getJobCategory() == null ? null :
                jobPreference.getJobCategory().getId());
        dto.setJobCategory(jobPreference.getJobCategory() == null ? null :
                jobCategoryMapper.toDto(new JobCategoryDTO(), jobPreference.getJobCategory()));

        // Industry
        dto.setIdIndustry(jobPreference.getIndustry() == null ? null :
                jobPreference.getIndustry().getId());
        dto.setIndustry(jobPreference.getIndustry() == null ? null :
                industryMapper.toDto(new IndustryDTO(), jobPreference.getIndustry()));

        // Preferred communication
        dto.setIdPreferredCommunication(jobPreference.getIdPreferredCommunication());
        dto.setPreferredCommunication(jobPreference.getIdPreferredCommunication() == null ? null :
                restCommunicationInfoClientService.getActivePreferredCommunicationById(jobPreference.getIdPreferredCommunication()));

        return dto;

    }

    /**
     * This method is allowed to convert model to dto
     *
     * @param jobPreferenceRequestDTO {@link JobPreferenceRequestDTO} - dto
     * @return {@link JobPreference}
     * @author @maleeshasa
     */
    public JobPreference dtoToModel(JobPreference jobPreference, JobPreferenceRequestDTO jobPreferenceRequestDTO, Candidate candidate) {
        log.info("JobPreferenceMapper.dtoToModel() => started.");

        jobPreference.setPreference(jobPreferenceRequestDTO.getPreference());
        jobPreference.setRemark(jobPreferenceRequestDTO.getRemark());
        jobPreference.setCandidate(candidate);

        log.info("JobPreferenceMapper.dtoToModel() => ended.");
        return jobPreference;

    }

}

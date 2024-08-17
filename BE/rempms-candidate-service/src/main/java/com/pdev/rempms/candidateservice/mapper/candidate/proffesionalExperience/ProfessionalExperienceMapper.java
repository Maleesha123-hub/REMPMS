package com.pdev.rempms.candidateservice.mapper.candidate.proffesionalExperience;

import com.pdev.rempms.candidateservice.dto.candidate.industry.IndustryDTO;
import com.pdev.rempms.candidateservice.dto.candidate.professionalExperience.ProfessionalExperienceRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.professionalExperience.ProfessionalExperienceResponseDTO;
import com.pdev.rempms.candidateservice.dto.jobCategory.JobCategoryDTO;
import com.pdev.rempms.candidateservice.mapper.candidate.industry.IndustryMapper;
import com.pdev.rempms.candidateservice.mapper.jobCategory.JobCategoryMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.Industry;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.JobCategory;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.ProfessionalExperience;
import com.pdev.rempms.candidateservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/04
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProfessionalExperienceMapper {

    private final IndustryMapper industryMapper;
    private final JobCategoryMapper jobCategoryMapper;

    /**
     * This method is allowed to map professional experience dto to model
     *
     * @param professionalExperience {@link ProfessionalExperience} - professional experience model
     * @param dto                    {@link ProfessionalExperienceRequestDTO} - professional experience dto
     * @param industry               {@link Industry} - industry of professional experience
     * @param jobCategory            {@link JobCategory} -  job category of professional experience
     * @return {@link ProfessionalExperience} - mapped model
     */
    public ProfessionalExperience toEntity(ProfessionalExperience professionalExperience, ProfessionalExperienceRequestDTO dto,
                                           Industry industry, JobCategory jobCategory, Candidate candidate) {
        log.info("ProfessionalExperienceMapper -> toEntity() => started!");

        professionalExperience.setIndustry(industry);
        professionalExperience.setOrganization(dto.getOrganization());
        professionalExperience.setJobCategory(jobCategory);
        professionalExperience.setDesignation(dto.getDesignation());
        professionalExperience.setCommencedDate(DateTimeUtil.getFormattedDate(dto.getCommencedDate()));
        professionalExperience.setCompletionDate(DateTimeUtil.getFormattedDate(dto.getCompletionDate()));
        professionalExperience.setDescription(dto.getDescription());
        professionalExperience.setStillWorking(dto.getStillWorking());
        professionalExperience.setCandidate(candidate);

        log.info("ProfessionalExperienceMapper -> toEntity() => ended!");
        return professionalExperience;
    }

    public ProfessionalExperienceResponseDTO toDto(ProfessionalExperienceResponseDTO dto,
                                                   ProfessionalExperience professionalExperience) {
        log.info("ProfessionalExperienceMapper -> toDto() => started!");

        dto.setIdProfessionalExperience(professionalExperience.getId());
        dto.setCommencedDate(DateTimeUtil.getFormattedDate(professionalExperience.getCommencedDate()));
        dto.setCompletionDate(DateTimeUtil.getFormattedDate(professionalExperience.getCompletionDate()));
        dto.setDescription(professionalExperience.getDescription());
        dto.setDesignation(professionalExperience.getDesignation());
        dto.setOrganization(professionalExperience.getOrganization());
        dto.setStillWorking(professionalExperience.getStillWorking());

        dto.setIdIndustry(professionalExperience.getIndustry() == null ? null : professionalExperience.getIndustry().getId());
        dto.setIndustry(industryMapper.toDto(new IndustryDTO(), professionalExperience.getIndustry()));

        dto.setIdJobCategory(professionalExperience.getJobCategory() == null ? null : professionalExperience.getJobCategory().getId());
        dto.setJobCategory(jobCategoryMapper.toDto(new JobCategoryDTO(), professionalExperience.getJobCategory()));

        log.info("ProfessionalExperienceMapper -> toDto() => ended!");
        return dto;

    }

}

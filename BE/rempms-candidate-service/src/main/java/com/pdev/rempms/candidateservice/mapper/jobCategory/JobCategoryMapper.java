package com.pdev.rempms.candidateservice.mapper.jobCategory;

import com.pdev.rempms.candidateservice.dto.jobCategory.JobCategoryDTO;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.JobCategory;
import com.pdev.rempms.candidateservice.util.CommonValidation;
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
public class JobCategoryMapper {

    public JobCategoryDTO toDto(JobCategoryDTO dto, JobCategory jobCategory) {
        log.info("JobCategoryMapper -> toDto() => started!");

        dto.setIdJobCategory(jobCategory.getId());
        dto.setJobCategoryName(jobCategory.getCategoryName());
        dto.setJobCategoryDescription(jobCategory.getDescription());
        dto.setCommonStatus(jobCategory.getCommonStatus());

        log.info("JobCategoryMapper -> toDto() => ended!");
        return dto;

    }

    public JobCategory toEntity(JobCategory jobCategory, JobCategoryDTO dto) {
        log.info("JobCategoryMapper -> toEntity() => started!");

        if (!CommonValidation.integerNullValidation(dto.getIdJobCategory())) {

            jobCategory.setId(dto.getIdJobCategory());

        }

        jobCategory.setCategoryName(dto.getJobCategoryName());
        jobCategory.setDescription(dto.getJobCategoryDescription());
        jobCategory.setCommonStatus(dto.getCommonStatus());

        log.info("JobCategoryMapper -> toEntity() => ended!");
        return jobCategory;

    }

}

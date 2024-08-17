package com.pdev.rempms.candidateservice.service.impl;

import com.pdev.rempms.candidateservice.constants.enums.CommonStatus;
import com.pdev.rempms.candidateservice.dto.jobCategory.JobCategoryDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.jobCategory.JobCategoryMapper;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.JobCategory;
import com.pdev.rempms.candidateservice.repository.JobCategoryRepository;
import com.pdev.rempms.candidateservice.service.JobCategoryService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryRepository jobCategoryRepository;
    private final JobCategoryMapper jobCategoryMapper;

    /**
     * Get all active job categories
     *
     * @return - All Active job categories data.
     * @author maleeshasa
     */
    @Override
    public CommonResponse getAllActive() {
        log.info("JobCategoryServiceImpl -> getAllActive() => started.");
        CommonResponse response = new CommonResponse();

        List<JobCategory> jobCategories = jobCategoryRepository.findAll();

        if (!jobCategories.isEmpty()) {
            log.info("JobCategoryServiceImpl -> getAllActive() => Job categories found.");

            List<JobCategoryDTO> jobCategoryDTOList = jobCategories.stream()
                    .filter(jobCategory -> jobCategory.getCommonStatus().equalsIgnoreCase(CommonStatus.ACTIVE.getValue()))
                    .map(jobCategory -> {
                        JobCategoryDTO dto = new JobCategoryDTO();
                        return jobCategoryMapper.toDto(dto, jobCategory);
                    })
                    .toList();

            response.setMessage("Job categories not found.");
            response.setStatus(HttpStatus.OK);
            response.setData(jobCategoryDTOList);

        } else {
            log.info("JobCategoryServiceImpl -> getAllActive() => Job categories not found.");

            throw new RecordNotFoundException("Job categories not found.");

        }

        log.info("JobCategoryServiceImpl -> getAllActive() => ended.");
        return response;

    }

}

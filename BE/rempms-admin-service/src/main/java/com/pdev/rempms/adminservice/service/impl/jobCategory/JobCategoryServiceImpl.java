package com.pdev.rempms.adminservice.service.impl.jobCategory;

import com.pdev.rempms.adminservice.constants.enums.CommonStatus;
import com.pdev.rempms.adminservice.constants.validation.CommonValidationMessage;
import com.pdev.rempms.adminservice.constants.validation.JobCategoryValidationMessages;
import com.pdev.rempms.adminservice.dto.jobCategory.JobCategoryDTO;
import com.pdev.rempms.adminservice.exception.RecordNotFoundException;
import com.pdev.rempms.adminservice.mapper.jobCategory.JobCategoryMapper;
import com.pdev.rempms.adminservice.model.AuditData;
import com.pdev.rempms.adminservice.model.JobCategory;
import com.pdev.rempms.adminservice.repository.JobCategoryRepository;
import com.pdev.rempms.adminservice.service.jobCategory.JobCategoryService;
import com.pdev.rempms.adminservice.util.CommonResponse;
import com.pdev.rempms.adminservice.util.CommonValidation;
import com.pdev.rempms.adminservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
     * save or update job category
     *
     * @param dto - job category saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> saveUpdate(JobCategoryDTO dto) {
        log.info("JobCategoryServiceImpl -> saveUpdate() => started!");
        CommonResponse commonResponse = new CommonResponse();
        JobCategory jobCategory = new JobCategory();
        String validation = validateJobCategory(dto);
        if (validation != null) {
            log.info("JobCategoryServiceImpl -> saveUpdate() => Validation available!");
            commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED);
            commonResponse.setMessage(validation);
            return new ResponseEntity<>(commonResponse, HttpStatus.OK);
        }
        if (!CommonValidation.stringNullValidation(dto.getIdJobCategory())) {
            log.info("JobCategoryServiceImpl -> saveUpdate() => Update existing job category!");
            jobCategory = jobCategoryRepository.findById(Long.valueOf(dto.getIdJobCategory())).get();
            jobCategory = jobCategoryMapper.toEntity(jobCategory, dto);
            jobCategory.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            jobCategory.getAuditData().setUpdatedBy(Long.valueOf(1)); //need further development for authorization
        } else {
            log.info("JobCategoryServiceImpl -> saveUpdate() => Save new job category!");
            AuditData auditData = new AuditData();
            auditData.setCreatedOn(DateTimeUtil.getSriLankaTime());
            auditData.setCreatedBy(Long.valueOf(1)); //need further development for authorization
            jobCategory.setAuditData(auditData);
            jobCategory = jobCategoryMapper.toEntity(jobCategory, dto);
        }
        jobCategoryRepository.save(jobCategory);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage(JobCategoryValidationMessages.CATEGORY_SAVE_SUCCESS);
        log.info("JobCategoryServiceImpl -> saveUpdate() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Validate job category save data
     *
     * @param dto
     * @return - Validation messages
     * @author maleeshasa
     */
    private String validateJobCategory(JobCategoryDTO dto) {
        log.info("JobCategoryServiceImpl -> ValidateJobCategory() => started!");
        String validation = null;
        if (CommonValidation.stringNullValidation(dto.getJobCategoryName())) {
            validation = CommonValidationMessage.NAME_EMPTY;
        } else if (CommonValidation.stringNullValidation(dto.getJobCategoryDescription())) {
            validation = CommonValidationMessage.DESCRIPTION_EMPTY;
        } else if (dto.getCommonStatus().equalsIgnoreCase("-1")) {
            validation = CommonValidationMessage.COMMON_STATUS_EMPTY;
        }
        log.info("JobCategoryServiceImpl -> ValidateJobCategory() => ended!");
        return validation;
    }

    /**
     * Get all active job categories
     *
     * @return - All Active job categories data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getAllActive() {
        log.info("JobCategoryServiceImpl -> getAllActive() => started.");
        CommonResponse commonResponse = new CommonResponse();
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
            commonResponse.setMessage(JobCategoryValidationMessages.CATEGORIES_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(jobCategoryDTOList);
        } else {
            log.info("JobCategoryServiceImpl -> getAllActive() => Job categories not found.");
            throw new RecordNotFoundException(JobCategoryValidationMessages.CATEGORIES_NOT_FOUND);
        }
        log.info("JobCategoryServiceImpl -> getAllActive() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get active job category by id
     *
     * @param idJobCategory - job category id
     * @return - Active job category data.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> getActiveById(Long idJobCategory) {
        log.info("JobCategoryServiceImpl -> getActiveById() => started!");
        CommonResponse commonResponse = new CommonResponse();
        JobCategory jobCategory = jobCategoryRepository.findByIdAndCommonStatus(idJobCategory, CommonStatus.ACTIVE.getValue());
        if (jobCategory != null) {
            log.info("JobCategoryServiceImpl -> getActiveById() => Job category found!");
            JobCategoryDTO dto = new JobCategoryDTO();
            dto = jobCategoryMapper.toDto(dto, jobCategory);
            commonResponse.setMessage(JobCategoryValidationMessages.CATEGORY_FOUND);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(dto);
        } else {
            log.info("JobCategoryServiceImpl -> getActiveById() => Job category not found!");
            throw new RecordNotFoundException(JobCategoryValidationMessages.CATEGORY_NOT_FOUND);
        }
        log.info("JobCategoryServiceImpl -> getActiveById() => ended!");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete job category by user account id
     *
     * @param idJobCategory - job category id
     * @return - Job category deleted success info.
     * @author maleeshasa
     */
    @Override
    public ResponseEntity<CommonResponse> deleteById(Long idJobCategory) {
        log.info("JobCategoryServiceImpl -> deleteById() => started.");
        CommonResponse commonResponse = new CommonResponse();
        Optional<JobCategory> jobCategory = jobCategoryRepository.findById(idJobCategory);
        if (jobCategory.isPresent()) {
            log.info("JobCategoryServiceImpl -> deleteById() => Job category found.");
            jobCategory.get().setCommonStatus(CommonStatus.DELETED.getValue());
            jobCategory.get().getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());
            jobCategory.get().getAuditData().setUpdatedBy(Long.valueOf(1)); //need further development for authorization
            jobCategoryRepository.save(jobCategory.get());
            commonResponse.setMessage(JobCategoryValidationMessages.CATEGORY_DELETE_SUCCESS);
            commonResponse.setStatus(HttpStatus.OK);
        } else {
            log.info("JobCategoryServiceImpl -> deleteById() => Job category not found.");
            throw new RecordNotFoundException(JobCategoryValidationMessages.CATEGORY_NOT_FOUND);
        }
        log.info("JobCategoryServiceImpl -> deleteById() => ended.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}

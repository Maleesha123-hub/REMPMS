package com.pdev.rempms.recruitmentservice.service.impl.jobVacancy;

import com.pdev.rempms.recruitmentservice.builder.UniqueNumberBuilder;
import com.pdev.rempms.recruitmentservice.constants.enums.DocumentType;
import com.pdev.rempms.recruitmentservice.constants.enums.FolderType;
import com.pdev.rempms.recruitmentservice.dto.jobVacancy.JobVacancySavedLazyResponseDTO;
import com.pdev.rempms.recruitmentservice.dto.document.upload.DocumentUploadResponseDTO;
import com.pdev.rempms.recruitmentservice.dto.jobVacancy.JobVacancyRequest;
import com.pdev.rempms.recruitmentservice.dto.jobVacancy.JobVacancyResponse;
import com.pdev.rempms.recruitmentservice.exception.BaseException;
import com.pdev.rempms.recruitmentservice.exception.RecordNotFoundException;
import com.pdev.rempms.recruitmentservice.mapper.jobVacancy.JobVacancyMapper;
import com.pdev.rempms.recruitmentservice.model.AuditData;
import com.pdev.rempms.recruitmentservice.model.employer.Employer;
import com.pdev.rempms.recruitmentservice.model.jobPosition.JobPosition;
import com.pdev.rempms.recruitmentservice.model.jobVacancy.JobVacancy;
import com.pdev.rempms.recruitmentservice.repository.employer.EmployerRepository;
import com.pdev.rempms.recruitmentservice.repository.jobPosition.JobPositionRepository;
import com.pdev.rempms.recruitmentservice.repository.jobVacancy.JobVacancyRepository;
import com.pdev.rempms.recruitmentservice.service.jobVacancy.JobVacancyService;
import com.pdev.rempms.recruitmentservice.service.rest.document.DocumentClientService;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import com.pdev.rempms.recruitmentservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobVacancyServiceImpl implements JobVacancyService {

    private final JobVacancyMapper jobVacancyMapper;
    private final JobVacancyRepository jobVacancyRepository;
    private final JobPositionRepository jobPositionRepository;
    private final EmployerRepository employerRepository;
    private final DocumentClientService documentClientService;
    private final UniqueNumberBuilder uniqueNumberBuilder;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse saveOrModify(JobVacancyRequest dto, MultipartFile file) {

        CommonResponse commonResponse = new CommonResponse();
        JobVacancy jobVacancy = new JobVacancy();
        JobPosition jobPosition = new JobPosition();
        Employer employer = new Employer();

        // Checking existing job vacancy
        if (dto.getId() != null) {
            jobVacancy = jobVacancyRepository.findById(dto.getId())
                    .orElseThrow(() -> new RecordNotFoundException("Job vacancy is not exists."));
            jobVacancy.getAuditData().setUpdatedOn(DateTimeUtil.getSriLankaTime());

            // Checking new job vacancy
        } else {
            AuditData auditData = new AuditData();
            auditData.setCreatedOn(DateTimeUtil.getSriLankaTime());
            jobVacancy.setAuditData(auditData);
        }

        // Fetching job position
        if (dto.getJobPositionId() != null) {
            jobPosition = jobPositionRepository.findById(dto.getJobPositionId())
                    .orElseThrow(() -> new RecordNotFoundException("Job position is not exists."));
        }

        // Fetching employer
        if (dto.getEmployerId() != null) {
            employer = employerRepository.findById(dto.getEmployerId())
                    .orElseThrow(() -> new RecordNotFoundException("Employer is not exists."));
        }

        // Saving job poster image in document service
        MultipartFile[] files = new MultipartFile[1];
        files[0] = file;

        List<DocumentUploadResponseDTO> uploadResponse = documentClientService.uploadDocuments(
                FolderType.EMPLOYER, employer.getEmployerNo(), DocumentType.JOB_POSTER.getValue(), files);

        // Mapping job vacancy object
        jobVacancyMapper.toEntity(jobVacancy, dto, jobPosition, employer, uploadResponse.getFirst());

        try {
            JobVacancy savedObj = jobVacancyRepository.save(jobVacancy);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Job vacancy is saved success.");
            commonResponse.setData(new JobVacancySavedLazyResponseDTO(savedObj.getId(), savedObj.getJobPosition().getPosition(),
                    savedObj.getEmployer().getEmployerName(), savedObj.getEmployer().getEmployerNo(), savedObj.isActive(),
                    savedObj.getRefNo(), savedObj.getPosterName()));

        } catch (Exception e) {
            // TODO implement fallback method for delete uploaded documents
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            commonResponse.setMessage("Job vacancy save failed.");
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getById(Integer id) {

        JobVacancy jobVacancy = jobVacancyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Job vacancy is not exists."));

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(jobVacancyMapper.toDto(new JobVacancyResponse(), jobVacancy));
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage("Job vacancy is exists.");
        return commonResponse;
    }

    @Override
    public CommonResponse deleteById(Integer id) {
        JobVacancy jobVacancy = jobVacancyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Job vacancy is not exists."));

        try {
            jobVacancy.setActive(Boolean.FALSE);
            jobVacancyRepository.save(jobVacancy);

            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Job vacancy deleted success.");
            return commonResponse;

        } catch (Exception e) {
            throw new BaseException(500, "Exception occurred while saving job vacancy. " + e.getMessage());
        }
    }

    @Override
    public CommonResponse getAll() {

        CommonResponse commonResponse = new CommonResponse();

        List<JobVacancyResponse> jobVacancies = jobVacancyRepository.findByActive(Boolean.TRUE).stream()
                .map(jobVacancy -> jobVacancyMapper.toDto(new JobVacancyResponse(), jobVacancy))
                .toList();

        if (!jobVacancies.isEmpty()) {
            commonResponse.setMessage("Job vacancies are exists.");
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setData(jobVacancies);

        } else {
            commonResponse.setMessage("Job vacancies are not exists.");
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setData(new ArrayList<>());
        }
        return commonResponse;
    }

}

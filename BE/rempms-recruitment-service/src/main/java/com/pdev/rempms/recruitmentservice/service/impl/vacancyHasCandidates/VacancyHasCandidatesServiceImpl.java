package com.pdev.rempms.recruitmentservice.service.impl.vacancyHasCandidates;

import com.pdev.rempms.recruitmentservice.constants.enums.DocumentType;
import com.pdev.rempms.recruitmentservice.constants.enums.FolderType;
import com.pdev.rempms.recruitmentservice.dto.candidate.CandidateDTO;
import com.pdev.rempms.recruitmentservice.dto.document.upload.DocumentUploadResponseDTO;
import com.pdev.rempms.recruitmentservice.dto.jobVacancy.JobVacancyResponse;
import com.pdev.rempms.recruitmentservice.dto.vacancyHasCandidates.VacancyHasCandidatesRequest;
import com.pdev.rempms.recruitmentservice.dto.vacancyHasCandidates.VacancyHasCandidatesResponse;
import com.pdev.rempms.recruitmentservice.exception.BaseException;
import com.pdev.rempms.recruitmentservice.exception.RecordNotFoundException;
import com.pdev.rempms.recruitmentservice.mapper.jobVacancy.JobVacancyMapper;
import com.pdev.rempms.recruitmentservice.mapper.vacancyHasCandidates.JobVacancyHasCandidatesMapper;
import com.pdev.rempms.recruitmentservice.model.AuditData;
import com.pdev.rempms.recruitmentservice.model.jobVacancy.JobVacancy;
import com.pdev.rempms.recruitmentservice.model.vacancyHasCandidates.VacancyHasCandidates;
import com.pdev.rempms.recruitmentservice.repository.jobVacancy.JobVacancyRepository;
import com.pdev.rempms.recruitmentservice.repository.vacancyHasCandidates.VacancyHasCandidatesRepository;
import com.pdev.rempms.recruitmentservice.service.rest.candidate.CandidateClientService;
import com.pdev.rempms.recruitmentservice.service.rest.document.DocumentClientService;
import com.pdev.rempms.recruitmentservice.service.vacancyHasCandidates.VacancyHasCandidatesService;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import com.pdev.rempms.recruitmentservice.util.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class VacancyHasCandidatesServiceImpl implements VacancyHasCandidatesService {

    private final JobVacancyHasCandidatesMapper jobVacancyHasCandidatesMapper;
    private final JobVacancyMapper jobVacancyMapper;

    private final DocumentClientService documentClientService;
    private final CandidateClientService candidateClientService;

    private final VacancyHasCandidatesRepository vacancyHasCandidatesRepository;
    private final JobVacancyRepository jobVacancyRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse saveOrModify(VacancyHasCandidatesRequest dto, MultipartFile[] file) {

        CommonResponse commonResponse = new CommonResponse();
        VacancyHasCandidates entity = new VacancyHasCandidates();
        List<DocumentUploadResponseDTO> uploadedFile = new ArrayList<>();

        JobVacancy jobVacancy = jobVacancyRepository.findById(dto.getJobVacancyId())
                .orElseThrow(() -> new RecordNotFoundException("Selected job vacancy is not exists."));

        CandidateDTO candidate = candidateClientService.getCandidateById(dto.getCandidateId());

        if (candidate == null) {
            throw new RecordNotFoundException("Candidate is not exists.");
        }

        if (!CommonValidation.stringNullValidation(dto.getCvUrl())) {
            entity.setCvUrl(dto.getCvUrl());

        } else if (file != null) {
            uploadedFile = documentClientService.uploadDocuments(
                    FolderType.CANDIDATE,
                    candidate.getCandidateNo(),
                    DocumentType.CV.getValue(),
                    file);
            if (uploadedFile != null) {
                entity.setCvUrl(uploadedFile.getFirst().getPathUrl());
            } else {
                throw new BaseException(500, "Document uploading fail.");
            }

        } else {
            throw new RecordNotFoundException("Please upload your curricular vitae.");
        }
        entity.setJobVacancy(jobVacancy);
        entity.setCandidateId(candidate.getIdCandidate());
        jobVacancyHasCandidatesMapper.dtoToModel(entity, dto);

        try {
            vacancyHasCandidatesRepository.save(entity);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Applied for the vacancy successfully.");
            commonResponse.setData(null);
            return commonResponse;

        } catch (Exception e) {
            // Deleting uploaded files for fallback
            deletingUploadedFiles(uploadedFile);
            throw new BaseException(500, "Applying for the vacancy failed.");
        }
    }

    private void deletingUploadedFiles(List<DocumentUploadResponseDTO> uploadedFile) {
        if (uploadedFile != null &&
                !CommonValidation.stringNullValidation(uploadedFile.getFirst().getPathUrl())) {
            CommonResponse response = documentClientService.deleteFiles(Collections.singletonList(uploadedFile.getFirst().getPathUrl()));
            log.info(response.getMessage());
        }
    }

    @Override
    public CommonResponse getById(Integer id) {

        JobVacancy jobVacancy = jobVacancyRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Vacancy is not exists."));

        List<VacancyHasCandidates> vacancyHasCandidates = vacancyHasCandidatesRepository.findByJobVacancy(jobVacancy);

        if (!vacancyHasCandidates.isEmpty()) {
            List<VacancyHasCandidatesResponse> responseList =
                    jobVacancyHasCandidatesMapper.modelToDtoList(vacancyHasCandidates);
            responseList.forEach(vacancyHasCandidatesResponse -> {
                vacancyHasCandidatesResponse.setCandidate(candidateClientService.getCandidateById(vacancyHasCandidatesResponse.getCandidateId()));
                vacancyHasCandidatesResponse.setJobVacancy(jobVacancyMapper.toDto(new JobVacancyResponse(), jobVacancy));
            });

            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setData(responseList);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Vacancy has candidates are exists.");
            return commonResponse;

        } else {
            throw new RecordNotFoundException("Vacancy has candidates not exists.");
        }
    }

}

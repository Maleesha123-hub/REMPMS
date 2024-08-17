package com.pdev.rempms.recruitmentservice.service.impl.jobPosition;

import com.pdev.rempms.recruitmentservice.dto.jobPosition.JobPositionRequestDTO;
import com.pdev.rempms.recruitmentservice.dto.jobPosition.JobPositionResponseDTO;
import com.pdev.rempms.recruitmentservice.exception.BaseException;
import com.pdev.rempms.recruitmentservice.exception.RecordNotFoundException;
import com.pdev.rempms.recruitmentservice.mapper.jobPosition.JobPositionMapper;
import com.pdev.rempms.recruitmentservice.model.jobPosition.JobPosition;
import com.pdev.rempms.recruitmentservice.repository.jobPosition.JobPositionRepository;
import com.pdev.rempms.recruitmentservice.repository.jobVacancy.JobVacancyRepository;
import com.pdev.rempms.recruitmentservice.service.jobPosition.JobPositionService;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobPositionServiceImpl implements JobPositionService {

    private final JobPositionRepository jobPositionRepository;
    private final JobPositionMapper jobPositionMapper;
    private final JobVacancyRepository jobVacancyRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse saveOrModify(JobPositionRequestDTO jobPositionRequest) {

        CommonResponse commonResponse = new CommonResponse();
        JobPosition jobPosition = new JobPosition();

        if (jobPositionRequest.getId() != null) {
            jobPosition = jobPositionRepository.findById(jobPositionRequest.getId())
                    .orElseThrow(() -> new RecordNotFoundException("Job position is not exists."));
        }

        try {
            JobPosition savedObj = jobPositionRepository.save(jobPositionMapper.toEntity(jobPosition,
                    jobPositionRequest));
            commonResponse.setData(jobPositionMapper.toDto(new JobPositionResponseDTO(), savedObj));
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Job position saved success.");

        } catch (Exception e) {
            throw new BaseException(500, "Exception occurred while saving job position. " + e.getMessage());
        }
        return commonResponse;
    }

    @Override
    public CommonResponse getById(Integer id) {
        JobPosition jobPosition = jobPositionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Job position is not exists."));

        JobPositionResponseDTO response = jobPositionMapper.toDto(new JobPositionResponseDTO(),
                jobPosition);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMessage("Job position is exists.");
        commonResponse.setData(response);
        commonResponse.setStatus(HttpStatus.OK);
        return commonResponse;
    }

    @Override
    public CommonResponse deleteById(Integer id) {
        CommonResponse commonResponse = new CommonResponse();
        JobPosition jobPosition = jobPositionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Job position is not exists."));

        if (!jobVacancyRepository.findByJobPosition(jobPosition).isEmpty()) {
            throw new BaseException(500, "Job position can not delete. It has job vacancies.");
        }

        try {
            jobPosition.setActive(Boolean.FALSE);
            jobPositionRepository.save(jobPosition);
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Job position deleted success.");
            return commonResponse;

        } catch (Exception e) {
            throw new BaseException(500, "Exception occurred while deleting job position. " + e.getMessage());
        }
    }

    @Override
    public CommonResponse getAll() {

        CommonResponse commonResponse = new CommonResponse();

        List<JobPositionResponseDTO> jobPositionList = jobPositionRepository.findByActive(Boolean.TRUE)
                .stream()
                .map(position -> jobPositionMapper.toDto(new JobPositionResponseDTO(), position))
                .toList();

        if (jobPositionList.isEmpty()) {
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Job positions are not exists.");
            commonResponse.setData(null);

        } else {
            commonResponse.setData(jobPositionList);
            commonResponse.setMessage("Job positions are exists.");
            commonResponse.setStatus(HttpStatus.OK);

        }
        return commonResponse;

    }

}

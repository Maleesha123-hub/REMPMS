package com.pdev.rempms.recruitmentservice.service.jobPosition;

import com.pdev.rempms.recruitmentservice.dto.jobPosition.JobPositionRequestDTO;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;

public interface JobPositionService {

    CommonResponse saveOrModify(JobPositionRequestDTO jobPositionRequest);

    CommonResponse getById(Integer id);

    CommonResponse deleteById(Integer id);

    CommonResponse getAll();
}

package com.pdev.rempms.recruitmentservice.service.jobVacancy;

import com.pdev.rempms.recruitmentservice.dto.jobVacancy.JobVacancyRequest;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface JobVacancyService {
    CommonResponse saveOrModify(JobVacancyRequest dto, MultipartFile file);

    CommonResponse getById(Integer id);

    CommonResponse deleteById(Integer id);

    CommonResponse getAll();

}

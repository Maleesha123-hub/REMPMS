package com.pdev.rempms.recruitmentservice.service.vacancyHasCandidates;

import com.pdev.rempms.recruitmentservice.dto.vacancyHasCandidates.VacancyHasCandidatesRequest;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

public interface VacancyHasCandidatesService {

    CommonResponse saveOrModify(VacancyHasCandidatesRequest dto, MultipartFile[] file);

    CommonResponse getById(Integer id);
}

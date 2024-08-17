package com.pdev.rempms.recruitmentservice.service.employer;

import com.pdev.rempms.recruitmentservice.dto.employer.EmployerDTO;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;

public interface EmployerService {
    CommonResponse save(EmployerDTO dto);

    CommonResponse getById(Integer id);

    CommonResponse deleteById(Integer id);

    CommonResponse getAll();
}

package com.pdev.rempms.recruitmentservice.mapper.employer;

import com.pdev.rempms.recruitmentservice.dto.employer.EmployerDTO;
import com.pdev.rempms.recruitmentservice.model.employer.Employer;
import org.springframework.stereotype.Component;

@Component
public class EmployerMapper {

    public Employer toEntity(Employer employer, EmployerDTO dto) {
        employer.setEmployerName(dto.getEmployerName());
        employer.setAddress(dto.getAddress());
        employer.setMobileNo(dto.getMobileNo());
        employer.setTelephoneNo(dto.getTelephoneNo());
        employer.setActive(Boolean.TRUE);
        return employer;
    }

    public EmployerDTO toDto(EmployerDTO dto, Employer employer) {
        dto.setId(employer.getId());
        dto.setAddress(employer.getAddress());
        dto.setTelephoneNo(employer.getTelephoneNo());
        dto.setMobileNo(employer.getMobileNo());
        dto.setEmployerName(employer.getEmployerName());
        dto.setActive(employer.isActive());
        dto.setEmployerNo(employer.getEmployerNo());
        return dto;
    }
}

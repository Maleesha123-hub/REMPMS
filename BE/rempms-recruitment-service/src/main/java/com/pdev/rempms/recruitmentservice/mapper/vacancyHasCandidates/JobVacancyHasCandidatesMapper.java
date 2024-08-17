package com.pdev.rempms.recruitmentservice.mapper.vacancyHasCandidates;

import com.pdev.rempms.recruitmentservice.dto.vacancyHasCandidates.VacancyHasCandidatesRequest;
import com.pdev.rempms.recruitmentservice.dto.vacancyHasCandidates.VacancyHasCandidatesResponse;
import com.pdev.rempms.recruitmentservice.mapper.generics.GenericMapper;
import com.pdev.rempms.recruitmentservice.model.vacancyHasCandidates.VacancyHasCandidates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JobVacancyHasCandidatesMapper
        extends GenericMapper<VacancyHasCandidates, VacancyHasCandidatesRequest, VacancyHasCandidatesResponse> {

    @Override
    public VacancyHasCandidates dtoToModel(VacancyHasCandidates model, VacancyHasCandidatesRequest dto) {
        model.setName(dto.getName());
        model.setDescription(dto.getDescription());
        model.setEmail(dto.getEmail());
        return model;
    }

    @Override
    public VacancyHasCandidatesResponse modelToDto(VacancyHasCandidates model) {
        VacancyHasCandidatesResponse dto = new VacancyHasCandidatesResponse();
        dto.setId(model.getId());
        dto.setDescription(model.getDescription());
        dto.setCvUrl(model.getCvUrl());
        dto.setEmail(model.getEmail());
        dto.setName(model.getName());
        dto.setCandidateId(model.getCandidateId());
        dto.setJobVacancyId(model.getJobVacancy() == null ? null : model.getJobVacancy().getId());
        return dto;
    }

    @Override
    public List<VacancyHasCandidates> dtoToModelList(List<VacancyHasCandidatesRequest> vacancyHasCandidatesRequests) {
        return List.of();
    }
}

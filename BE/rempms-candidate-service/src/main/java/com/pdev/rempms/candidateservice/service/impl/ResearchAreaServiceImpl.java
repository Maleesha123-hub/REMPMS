package com.pdev.rempms.candidateservice.service.impl;

import com.pdev.rempms.candidateservice.dto.researchArea.ResearchAreaDTO;
import com.pdev.rempms.candidateservice.mapper.researchArea.ResearchAreaMapper;
import com.pdev.rempms.candidateservice.model.candidate.research.ResearchArea;
import com.pdev.rempms.candidateservice.repository.ResearchAreaRepository;
import com.pdev.rempms.candidateservice.service.ResearchAreaService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResearchAreaServiceImpl implements ResearchAreaService {

    private final ResearchAreaRepository researchAreaRepository;
    private final ResearchAreaMapper researchAreaMapper;

    @Override
    public CommonResponse getActiveResearchAreas() {

        CommonResponse response = new CommonResponse();
        List<ResearchArea> researchAreas = researchAreaRepository.findAll();

        if (!researchAreas.isEmpty()) {

            List<ResearchAreaDTO> researchAreaDTOS = researchAreas.stream()
                    .map(researchArea -> researchAreaMapper.toDto(new ResearchAreaDTO(), researchArea)).toList();

            response.setData(researchAreaDTOS);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Research areas are exists.");

        } else {

            response.setData(null);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Research areas are not exists.");

        }

        return response;

    }

}

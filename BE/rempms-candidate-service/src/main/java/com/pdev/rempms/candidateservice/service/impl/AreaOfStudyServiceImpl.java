package com.pdev.rempms.candidateservice.service.impl;

import com.pdev.rempms.candidateservice.dto.candidate.areaOfStudy.AreaOfStudyDTO;
import com.pdev.rempms.candidateservice.mapper.areaOfStudy.AreaOfStudyMapper;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.AreaOfStudy;
import com.pdev.rempms.candidateservice.repository.AreaOfStudyRepository;
import com.pdev.rempms.candidateservice.service.AreaOfStudyService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AreaOfStudyServiceImpl implements AreaOfStudyService {

    private final AreaOfStudyRepository areaOfStudyRepository;
    private final AreaOfStudyMapper areaOfStudyMapper;

    /**
     * This method is allowed to get active area of studies
     *
     * @return {@link CommonResponse}
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getActiveAreaOfStudies() {
        log.info("AreaOfStudyServiceImpl.getActiveAreaOfStudies() => started.");

        CommonResponse response = new CommonResponse();

        List<AreaOfStudy> areaOfStudies = areaOfStudyRepository.findAll();

        if (!areaOfStudies.isEmpty()) {
            log.warn("Area of studies are exists.");

            List<AreaOfStudyDTO> dtoList = areaOfStudies.stream()
                    .map(areaOfStudy -> areaOfStudyMapper.toDto(new AreaOfStudyDTO(), areaOfStudy)).toList();

            response.setData(dtoList);
            response.setMessage("Area of studies are exists.");
            response.setStatus(HttpStatus.OK);

        } else {
            log.warn("Area of studies not exists.");

            response.setData(null);
            response.setMessage("Area of studies not exists.");
            response.setStatus(HttpStatus.NOT_FOUND);

        }

        log.info("AreaOfStudyServiceImpl.getActiveAreaOfStudies() => ended.");
        return response;

    }

}

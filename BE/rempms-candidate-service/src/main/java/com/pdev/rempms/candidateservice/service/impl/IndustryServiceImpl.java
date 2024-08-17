package com.pdev.rempms.candidateservice.service.impl;

import com.pdev.rempms.candidateservice.dto.candidate.industry.IndustryDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.industry.IndustryMapper;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.Industry;
import com.pdev.rempms.candidateservice.repository.IndustryRepository;
import com.pdev.rempms.candidateservice.service.IndustryService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author @maleeshasa
 * @Date 2024/03/07
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository industryRepository;
    private final IndustryMapper industryMapper;

    /**
     * This method is allowed to get active industries
     *
     * @return {@link ResponseEntity <CommonResponse>}
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getActiveIndustries() {
        log.info("IndustryServiceImpl.getActiveIndustries() => started.");

        CommonResponse response = new CommonResponse();

        List<Industry> industries = industryRepository.findAll();

        if (!industries.isEmpty()) {
            log.warn("Industries are exists.");

            List<IndustryDTO> dtoList = industries.stream()
                    .map(industry -> industryMapper.toDto(new IndustryDTO(), industry)).toList();

            response.setData(dtoList);
            response.setMessage("Industries are exists.");
            response.setStatus(HttpStatus.OK);

        } else {
            log.warn("Industries not exists.");

            response.setData(null);
            response.setMessage("Industries not exists.");
            response.setStatus(HttpStatus.NOT_FOUND);

        }

        log.info("IndustryServiceImpl.getActiveIndustries() => ended.");
        return response;

    }

    @Override
    public CommonResponse getById(Integer id) {

        Industry industry = industryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Industry is not exists."));

        IndustryDTO dto = industryMapper.toDto(new IndustryDTO(), industry);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(dto);
        commonResponse.setMessage("Industry is exists.");
        commonResponse.setStatus(HttpStatus.OK);
        return commonResponse;
    }

}

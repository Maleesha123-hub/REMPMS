package com.pdev.rempms.candidateservice.service.impl;

import com.pdev.rempms.candidateservice.dto.scheme.SchemeDTO;
import com.pdev.rempms.candidateservice.mapper.scheme.SchemeMapper;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.Scheme;
import com.pdev.rempms.candidateservice.repository.SchemeRepository;
import com.pdev.rempms.candidateservice.service.SchemeService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 22/03/2024
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchemeServiceImpl implements SchemeService {

    private final SchemeRepository schemeRepository;
    private final SchemeMapper schemeMapper;

    @Override
    public CommonResponse getBySchoolEduQualification(String schoolEduQualification) {

        CommonResponse response = new CommonResponse();
        List<Scheme> schemes = schemeRepository.findBySchoolEduQualification(schoolEduQualification);

        if (!schemes.isEmpty()) {

            List<SchemeDTO> schemeDTOS = schemes.stream()
                    .map(scheme -> schemeMapper.toDto(new SchemeDTO(), scheme)).toList();

            response.setData(schemeDTOS);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Schemes are exists for school edu qualification.");

        } else {

            response.setData(null);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Schemes are not exists for school edu qualification.");

        }

        return response;

    }

}

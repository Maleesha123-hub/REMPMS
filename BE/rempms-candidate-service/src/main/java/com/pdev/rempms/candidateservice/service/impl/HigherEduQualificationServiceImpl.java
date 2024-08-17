package com.pdev.rempms.candidateservice.service.impl;

import com.pdev.rempms.candidateservice.dto.candidate.higherEducation.HigherEduQualificationDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.higherEducation.HigherEduQualificationMapper;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEduQualification;
import com.pdev.rempms.candidateservice.repository.HigherEduQualificationRepository;
import com.pdev.rempms.candidateservice.service.HigherEduQualificationService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/03/12
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class HigherEduQualificationServiceImpl implements HigherEduQualificationService {

    private final HigherEduQualificationRepository higherEduQualificationRepository;
    private final HigherEduQualificationMapper higherEduQualificationMapper;

    /**
     * This method is allowed to get active higher education qualifications
     *
     * @return {@link CommonResponse}
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getAllActiveHigherEduQualification() {
        log.info("HigherEducationServiceImpl.getAllActiveHigherEduQualification() => started.");

        CommonResponse response = new CommonResponse();

        List<HigherEduQualification> higherEduQualifications = higherEduQualificationRepository.findAll();

        if (!higherEduQualifications.isEmpty()) {
            log.warn("Higher educations are exists.");

            List<HigherEduQualificationDTO> dtoList = higherEduQualifications.stream()
                    .map(higherEduQualification -> higherEduQualificationMapper.toDto(new HigherEduQualificationDTO(), higherEduQualification)).toList();

            response.setData(dtoList);
            response.setMessage("Higher educations are exists.");
            response.setStatus(HttpStatus.OK);

        } else {
            log.warn("Higher educations not exists.");

            response.setData(null);
            response.setMessage("Higher educations not exists.");
            response.setStatus(HttpStatus.NOT_FOUND);

        }

        log.info("HigherEducationServiceImpl.getAllActiveHigherEduQualification() => ended.");
        return response;

    }

    /**
     * This method is allowed to get higher education qualification by id
     *
     * @return {@link ResponseEntity <CommonResponse>}
     * @author @maleeshasa
     */
    @Override
    public CommonResponse getById(Integer id) {
        log.info("HigherEducationServiceImpl.getById() => started.");

        CommonResponse response = new CommonResponse();

        HigherEduQualification higherEduQualification = higherEduQualificationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Higher education qualification not exists."));

        response.setStatus(HttpStatus.OK);
        response.setMessage("Higher education qualification is exists.");
        response.setData(higherEduQualificationMapper.toDto(new HigherEduQualificationDTO(), higherEduQualification));

        log.info("HigherEducationServiceImpl.getById() => ended.");
        return response;

    }

}

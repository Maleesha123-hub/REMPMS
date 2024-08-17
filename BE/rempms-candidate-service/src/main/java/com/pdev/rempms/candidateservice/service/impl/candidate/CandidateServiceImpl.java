package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.builder.UniqueNumberBuilder;
import com.pdev.rempms.candidateservice.dto.candidate.CandidateDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.CandidateMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.service.candidate.CandidateService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Service
@Slf4j
@Data
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final UniqueNumberBuilder uniqueNumberBuilder;
    private String message;

    /**
     * save candidate
     *
     * @param dto - candidate data
     * @return - {@link CommonResponse} - save success info.
     * @author @maleeshasa
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse saveCandidate(CandidateDTO dto) {
        log.info("CandidateServiceImpl -> saveCandidate() => started!");

        CandidateDTO candidateDTO = new CandidateDTO();
        CommonResponse commonResponse = new CommonResponse();

        try {
            message = "Candidate created successfully.";

            // Generate a reference no for candidate
            String refNo = uniqueNumberBuilder.generateNextReference("CAN", String.valueOf(Year.now().getValue()));

            Candidate candidate = candidateMapper.toEntity(new Candidate(), dto, refNo);
            Candidate createdCandidate = candidateRepository.save(candidate);
            candidateMapper.toDto(candidateDTO, createdCandidate);

            commonResponse.setData(candidateDTO);
            commonResponse.setStatus(HttpStatus.CREATED);
            commonResponse.setMessage(message);

        } catch (Exception e) {
            message = "Candidates created failed.";

            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            commonResponse.setMessage(message);

        }

        log.info("CandidateServiceImpl -> saveCandidate() => ended!");
        return commonResponse;

    }

    /**
     * This method is allowed to get candidate details by id
     *
     * @param idCandidate {@link Integer} - candidate id
     * @return - {@link CommonResponse} - candidate response.
     * @author @Maleesha99
     */
    @Override
    public CommonResponse getById(Integer idCandidate) {
        log.info("CandidateServiceImpl -> getById() => started!");

        Candidate candidate = candidateRepository.findById(idCandidate)
                .orElseThrow(() -> new RecordNotFoundException("Candidate is not exists."));

        CommonResponse response = new CommonResponse();
        response.setData(candidateMapper.toDto(new CandidateDTO(), candidate));
        response.setStatus(HttpStatus.OK);
        response.setMessage("Candidate is exists.");

        log.info("CandidateServiceImpl -> getById() => ended!");
        return response;

    }

}

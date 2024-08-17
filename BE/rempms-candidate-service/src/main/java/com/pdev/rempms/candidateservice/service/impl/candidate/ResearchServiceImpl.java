package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.research.ResearchMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.research.Research;
import com.pdev.rempms.candidateservice.model.candidate.research.ResearchArea;
import com.pdev.rempms.candidateservice.repository.ResearchAreaRepository;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.repository.candidate.ResearchRepository;
import com.pdev.rempms.candidateservice.service.candidate.ResearchService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/09
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResearchServiceImpl implements ResearchService {

    private final ResearchAreaRepository researchAreaRepository;
    private final CandidateRepository candidateRepository;
    private final ResearchRepository researchRepository;
    private final ResearchMapper researchMapper;
    private String message;

    /**
     * This method is allowed to save or update researches
     *
     * @param dto {@link CommonProfileRequestDTO} - researches saved response
     * @return {@link CommonResponse} - researches saved response
     * @author @Maleesha99
     */
    @Override
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("ResearchServiceImpl.saveUpdate() => started.");

        CommonResponse response = new CommonResponse();
        List<Research> researchList = new ArrayList<>();

        try {
            message = "Researches saved success.";

            log.info("Fetching candidate by id...");
            Candidate candidate = candidateRepository.findById(dto.getIdCandidate())
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));
            log.info("Candidate is fetched by id.");

            dto.getResearches().forEach(

                    researchDTO -> {

                        log.info("Get research area by id...");
                        ResearchArea researchArea = researchAreaRepository.findById(researchDTO.getIdResearchArea())
                                .orElseThrow(() -> new RecordNotFoundException("Research area is not exists."));
                        log.info("Fetched by id.");

//                        Research research = researchMapper.dtoToModel(researchDTO);
//                        research.setResearchArea(researchArea);
//                        research.setCandidate(candidate);
//                        researchList.add(research);

                    }

            );

            log.info("Saving researches details...");
            researchRepository.saveAll(researchList);
            log.info("Researches details are saved success.");

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "Researches already updated.";

            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "Researches details created failed.";

            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);

        }

        log.info("ResearchServiceImpl.saveUpdate() => ended.");
        return response;

    }

}

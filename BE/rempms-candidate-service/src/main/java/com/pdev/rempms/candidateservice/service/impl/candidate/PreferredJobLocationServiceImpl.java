package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.prefferedJobLocation.PreferredJobLocationMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.prefferedJobLocation.PreferredJobLocation;
import com.pdev.rempms.candidateservice.repository.JobLocationRepository;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.repository.candidate.PreferredJobLocationRepository;
import com.pdev.rempms.candidateservice.service.candidate.PreferredJobLocationService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PreferredJobLocationServiceImpl implements PreferredJobLocationService {

    private final PreferredJobLocationMapper preferredJobLocationMapper;
    private final CandidateRepository candidateRepository;
    private final PreferredJobLocationRepository preferredJobLocationRepository;
    private final JobLocationRepository jobLocationRepository;
    private String message;

    /**
     * This method is allowed to save or update preferred job locations
     *
     * @param dto {@link CommonProfileRequestDTO} - including preferred job locations details
     * @return {@link CommonResponse} - preferred job locations saved response
     * @author @Maleesha99
     */
    @Override
    @Transactional
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("PreferredJobLocationServiceImpl.saveUpdate() => started.");

        CommonResponse response = new CommonResponse();
        List<PreferredJobLocation> preferredJobLocations = new ArrayList<>();

        try {
            message = "Preferred job locations saved success.";

            log.info("Fetching candidate by id...");
            Candidate candidate = candidateRepository.findById(Integer.valueOf(dto.getIdCandidate()))
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));
            log.info("Candidate is fetched by id.");

//            dto.getPreferredJobLocations().forEach(preferredJobLocationDTO -> {
//
//                log.info("Get job location by id...");
//                JobLocation jobLocation = jobLocationRepository.findById(Long.valueOf(preferredJobLocationDTO.getIdJobLocation()))
//                        .orElseThrow(() -> new RecordNotFoundException("Job location not exist!"));
//                log.info("Job location is fetched by id.");
//
//                log.info("Mapping preferred job location...");
//                PreferredJobLocation preferredJobLocation = preferredJobLocationMapper.dtoToModel(preferredJobLocationDTO);
//                preferredJobLocation.setCandidate(candidate);
//                preferredJobLocation.setJobLocation(jobLocation);
//                preferredJobLocations.add(preferredJobLocation);
//                log.info("Preferred job location is mapped.");
//
//            });

            log.info("Saving preferred job locations details...");
            preferredJobLocationRepository.saveAll(preferredJobLocations);
            log.info("Preferred job locations details are saved success.");

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "Preferred job locations already updated.";

            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "Preferred job locations details created failed.";

            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);

        }

        log.info("PreferredJobLocationServiceImpl.saveUpdate() => ended.");
        return response;

    }

}

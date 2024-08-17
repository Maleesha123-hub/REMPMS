package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.familyInformation.FamilyInformationMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.familyInformation.FamilyInformation;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.repository.candidate.FamilyInformationRepository;
import com.pdev.rempms.candidateservice.service.candidate.FamilyInformationService;
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
public class FamilyInformationServiceImpl implements FamilyInformationService {

    private final FamilyInformationMapper familyInformationMapper;
    private final FamilyInformationRepository familyInformationRepository;
    private final CandidateRepository candidateRepository;
    private String message;

    /**
     * This method is allowed to save or update family information
     *
     * @param dto {@link CommonProfileRequestDTO} - including family information details
     * @return {@link CommonResponse} - family information saved response
     * @author @Maleesha99
     */
    @Override
    @Transactional
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("FamilyInformationServiceImpl.saveUpdate() => started.");

        CommonResponse response = new CommonResponse();
        List<FamilyInformation> familyInformationList = new ArrayList<>();

        try {
            message = "Family information saved success.";

            log.info("Fetching candidate by id...");
            Candidate candidate = candidateRepository.findById(Integer.valueOf(dto.getIdCandidate()))
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));
            log.info("Candidate is fetched by id.");

            dto.getFamilyInformation().forEach(familyInformationDTO -> {

//                log.info("Mapping family information...");
//                FamilyInformation familyInformation = familyInformationMapper.dtoToModel(familyInformationDTO);
//                familyInformation.setCandidate(candidate);
//                familyInformationList.add(familyInformation);
//                log.info("Family information is mapped.");

            });

            log.info("Saving family information details...");
            familyInformationRepository.saveAll(familyInformationList);
            log.info("Family information details are saved success.");

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "Family information already updated.";

            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "Family information details created failed.";

            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);

        }

        log.info("FamilyInformationServiceImpl.saveUpdate() => ended.");
        return response;

    }

}

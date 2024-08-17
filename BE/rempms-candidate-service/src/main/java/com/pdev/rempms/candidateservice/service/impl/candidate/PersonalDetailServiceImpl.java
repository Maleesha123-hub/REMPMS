package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.dto.communication.CommunicationInformationRequestDTO;
import com.pdev.rempms.candidateservice.dto.location.LocationInformationRequestDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.personalDetail.PersonalDetailMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.personalDetail.PersonalDetail;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.repository.candidate.PersonalDetailRepository;
import com.pdev.rempms.candidateservice.service.candidate.PersonalDetailService;
import com.pdev.rempms.candidateservice.service.rest.RestCommunicationInfoClientService;
import com.pdev.rempms.candidateservice.service.rest.RestLocationInfoClientService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalDetailServiceImpl implements PersonalDetailService {

    private final PersonalDetailRepository personalDetailRepository;
    private final PersonalDetailMapper personalDetailMapper;
    private final CandidateRepository candidateRepository;
    private final RestLocationInfoClientService restLocationInfoClientService;
    private final RestCommunicationInfoClientService restCommunicationInfoClientService;
    private String message;

    /**
     * This method is allowed to save update personal details of candidate
     *
     * @param dto {@link CommonProfileRequestDTO} - including personal details
     * @return - {@link CommonResponse} - save success info.
     * @author @Maleesha99
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("PersonalDetailServiceImpl.saveUpdate => started.");

        CommonResponse response = new CommonResponse();
        try {
            message = "Personal detail created success.";
            Candidate candidate = candidateRepository.findById(Integer.valueOf(dto.getIdCandidate()))
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));

            log.info("Location information saving...");
            LocationInformationRequestDTO location = restLocationInfoClientService.saveLocationInfo(dto.getPersonalDetail());
            Long idLocation = Long.valueOf(location.getIdLocationInformation());
            log.info("Location information saved.");

            log.info("Communication information saving...");
            CommunicationInformationRequestDTO communication = restCommunicationInfoClientService.saveCommunicationInfo(dto.getPersonalDetail());
            Long idCommunication = Long.valueOf(communication.getIdCommunicationInformation());
            log.info("Communication information saved.");

            PersonalDetail createdPersonalDetail = null;

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);
            response.setData(createdPersonalDetail.getId());

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "Personal detail already updated.";
            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "Personal detail created failed.";
            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);
        }

        return response;

    }
}

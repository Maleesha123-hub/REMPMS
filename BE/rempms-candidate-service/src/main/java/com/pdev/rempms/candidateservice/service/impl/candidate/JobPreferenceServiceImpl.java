package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.dto.communication.preferredCommunication.PreferredCommunicationDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.jobPreference.JobPreferenceMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.jobPreference.JobPreference;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.Industry;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.JobCategory;
import com.pdev.rempms.candidateservice.repository.IndustryRepository;
import com.pdev.rempms.candidateservice.repository.JobCategoryRepository;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.repository.candidate.JobPreferenceRepository;
import com.pdev.rempms.candidateservice.service.candidate.JobPreferenceService;
import com.pdev.rempms.candidateservice.service.rest.RestCommunicationInfoClientService;
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
public class JobPreferenceServiceImpl implements JobPreferenceService {

    private final JobPreferenceMapper jobPreferenceMapper;
    private final CandidateRepository candidateRepository;
    private final JobPreferenceRepository jobPreferenceRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final IndustryRepository industryRepository;
    private final RestCommunicationInfoClientService restCommunicationInfoClientService;
    private String message;

    /**
     * This method is allowed to save or update job preferences
     *
     * @param dto {@link CommonProfileRequestDTO} - including job preferences details
     * @return {@link CommonResponse} - job preferences saved response
     * @author @Maleesha99
     */
    @Override
    @Transactional
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("JobPreferenceServiceImpl.saveUpdate() => started.");

        CommonResponse response = new CommonResponse();
        List<JobPreference> jobPreferenceList = new ArrayList<>();

        try {
            message = "Job preferences saved success.";

            log.info("Fetching candidate by id...");
            Candidate candidate = candidateRepository.findById(Integer.valueOf(dto.getIdCandidate()))
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));
            log.info("Candidate is fetched by id.");

            dto.getJobPreferences().forEach(jobPreferenceDTO -> {

                log.info("Get job category by id...");
                JobCategory jobCategory = jobCategoryRepository.findById(Integer.valueOf(jobPreferenceDTO.getIdJobCategory()))
                        .orElseThrow(() -> new RecordNotFoundException("Job category not exist!"));
                log.info("Job category is fetched by id.");

                log.info("Get industry by id...");
                Industry industry = industryRepository.findById(jobPreferenceDTO.getIdIndustry())
                        .orElseThrow(() -> new RecordNotFoundException("Industry not exist!"));
                log.info("Industry is fetched by id.");

                log.info("Get preferred communication by id from communication service...");
                PreferredCommunicationDTO preferredCommunication = restCommunicationInfoClientService.getActivePreferredCommunicationById(
                        jobPreferenceDTO.getIdPreferredCommunication());
                log.info("Preferred communication is fetched by id.");

                if (preferredCommunication == null) {
                    log.warn("Given preferred communication is not exists in exact data.");

                    throw new RecordNotFoundException("Given preferred communication is not exists in exact data.");

                }

//                log.info("Mapping job preference...");
//                JobPreference jobPreference = jobPreferenceMapper.dtoToModel(jobPreferenceDTO);
//                jobPreference.setCandidate(candidate);
//                jobPreference.setIndustry(industry);
//                jobPreference.setJobCategory(jobCategory);
//                jobPreference.setIdPreferredCommunication(Long.valueOf(preferredCommunication.getIdPreferredCommunication()));
//
//                log.info("Job preference is mapped.");
//
//                jobPreferenceList.add(jobPreference);

            });

            log.info("Saving job preferences details...");
            jobPreferenceRepository.saveAll(jobPreferenceList);
            log.info("job preferences details are saved success.");

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "Job preference already updated.";

            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "Job preference details created failed.";

            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);

        }

        log.info("JobPreferenceServiceImpl.saveUpdate() => ended.");
        return response;

    }

}

package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.proffesionalExperience.ProfessionalExperienceMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.Industry;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.JobCategory;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.ProfessionalExperience;
import com.pdev.rempms.candidateservice.repository.IndustryRepository;
import com.pdev.rempms.candidateservice.repository.JobCategoryRepository;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.repository.candidate.ProfessionalExperienceRepository;
import com.pdev.rempms.candidateservice.service.candidate.ProfessionalExperienceService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/04
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProfessionalExperienceServiceImpl implements ProfessionalExperienceService {

    private final CandidateRepository candidateRepository;
    private final IndustryRepository industryRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final ProfessionalExperienceMapper professionalExperienceMapper;
    private final ProfessionalExperienceRepository professionalExperienceRepository;
    private String message;

    /**
     * This method is allowed to save update professional experiences of candidate
     *
     * @param dto {@link CommonProfileRequestDTO} - including professional experiences details
     * @return - {@link CommonResponse} - save success info.
     * @author @Maleesha99
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("ProfessionalExperienceServiceImpl.saveUpdate() => started.");

        CommonResponse response = new CommonResponse();
        List<ProfessionalExperience> professionalExperiences = new ArrayList<>();
        try {
            message = "Professional experiences saved success.";

            log.info("Fetching candidate by id...");
            Candidate candidate = candidateRepository.findById(dto.getIdCandidate())
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));
            log.info("Candidate is fetched by id.");

            dto.getProfessionalExperiences().forEach(professionalExperience -> {

                        log.info("Fetching industry by id...");
                        Industry industry = industryRepository.findById(professionalExperience.getIdIndustry())
                                .orElseThrow(() -> new RecordNotFoundException("Industry not found!"));
                        log.info("Industry is fetched by id.");

                        log.info("Fetching job category by id...");
                        JobCategory jobCategory = jobCategoryRepository.findById(professionalExperience.getIdJobCategory())
                                .orElseThrow(() -> new RecordNotFoundException("Job category not found!"));
                        log.info("Job category is fetched by id.");

                        log.info("Mapping professional experience...");
                        professionalExperiences.add(professionalExperienceMapper.toEntity(new ProfessionalExperience(), professionalExperience,
                                industry, jobCategory, candidate));
                        log.info("Professional experience is mapped.");

                    }
            );

            log.info("Saving professional experiences...");
            professionalExperienceRepository.saveAll(professionalExperiences);
            log.info("Professional experiences are saved success.");

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "Professional experiences already updated.";

            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "Professional experiences created failed.";

            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);
        }

        log.info("ProfessionalExperienceServiceImpl.saveUpdate() => ended.");
        return response;

    }

}

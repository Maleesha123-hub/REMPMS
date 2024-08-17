package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.higherEducation.HigherEducationMapper;
import com.pdev.rempms.candidateservice.mapper.higherEducation.HigherEduQualificationMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.AreaOfStudy;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEduQualification;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEducation;
import com.pdev.rempms.candidateservice.repository.AreaOfStudyRepository;
import com.pdev.rempms.candidateservice.repository.HigherEduQualificationRepository;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.repository.candidate.HigherEducationRepository;
import com.pdev.rempms.candidateservice.service.candidate.HigherEducationService;
import com.pdev.rempms.candidateservice.service.rest.RestCommunicationInfoClientService;
import com.pdev.rempms.candidateservice.service.rest.RestLocationInfoClientService;
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
 * @Date 2024/02/04
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HigherEducationServiceImpl implements HigherEducationService {

    private final CandidateRepository candidateRepository;
    private final HigherEducationRepository higherEducationRepository;
    private final HigherEduQualificationRepository higherEduQualificationRepository;
    private final AreaOfStudyRepository areaOfStudyRepository;
    private final RestLocationInfoClientService restLocationInfoClientService;
    private final RestCommunicationInfoClientService restCommunicationInfoClientService;
    private final HigherEducationMapper higherEducationMapper;
    private final HigherEduQualificationMapper higherEduQualificationMapper;
    private String message;

    /**
     * This method is allowed to save higher education details
     *
     * @param dto {@link CommonProfileRequestDTO} - including higher education details
     * @return {@link CommonResponse} - higher education saved response
     * @author @Maleesha99
     */
    @Override
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("HigherEducationServiceImpl.saveUpdate() => started.");

        CommonResponse response = new CommonResponse();
        List<HigherEducation> higherEducationList = new ArrayList<>();

        try {
            message = "Higher education saved success.";

            log.info("Fetching candidate by id...");
            Candidate candidate = candidateRepository.findById(Integer.valueOf(dto.getIdCandidate()))
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));
            log.info("Candidate is fetched by id.");

            dto.getHigherEducations().forEach(

                    higherEducation -> {

                        log.info("Fetching education qualification by id...");
                        HigherEduQualification higherEduQualification = higherEduQualificationRepository.findById(Integer.valueOf(higherEducation.getIdHigherEduQualification()))
                                .orElseThrow(() -> new RecordNotFoundException("Higher education qualification not found!"));
                        log.info("Education qualification is fetched by id.");

                        log.info("Fetching area of study by id...");
                        AreaOfStudy areaOfStudy = areaOfStudyRepository.findById(higherEducation.getIdAreaOfStudy())
                                .orElseThrow(() -> new RecordNotFoundException("Area of study not found!"));
                        log.info("Area of study is fetched by id.");

                        log.info("Get country by id from location service...");
                        CountryDTO country = restLocationInfoClientService.getById(higherEducation.getIdCountry());
                        log.info("Country is fetched by id.");

                        if (country == null) {
                            log.warn("Country is null by country id from location service.");

                            throw new RecordNotFoundException("Given country is not exists in exact country data from location service.");

                        }

                        log.info("Get language by id from communication service...");
                        LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(higherEducation.getIdLanguage());
                        log.info("Language is fetched by id.");

                        if (language == null) {
                            log.warn("Language is null by language id from communication service.");

                            throw new RecordNotFoundException("Given language is not exists in exact language data from communication service.");

                        }

                        log.info("Mapping higher education...");
                        higherEducationList.add(higherEducationMapper.toEntity(new HigherEducation(), higherEducation, higherEduQualification,
                                areaOfStudy, country, language, candidate));
                        log.info("Higher education is mapped.");

                    }
            );

            log.info("Saving higher education details...");
            higherEducationRepository.saveAll(higherEducationList);
            log.info("Higher education details are saved success.");

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "Higher education already updated.";

            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "Higher education details created failed.";

            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);
        }

        log.info("HigherEducationServiceImpl.saveUpdate() => ended.");
        return response;

    }

}

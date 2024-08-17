package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.languageProfficiency.LanguageProficiency;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.repository.candidate.LanguageProficiencyRepository;
import com.pdev.rempms.candidateservice.service.candidate.LanguageProficiencyService;
import com.pdev.rempms.candidateservice.service.rest.RestCommunicationInfoClientService;
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
public class LanguageProficiencyServiceImpl implements LanguageProficiencyService {

    private final LanguageProficiencyRepository languageProficiencyRepository;
    private final CandidateRepository candidateRepository;
    private final RestCommunicationInfoClientService restCommunicationInfoClientService;
    private String message;

    /**
     * This method is allowed to save or update language proficiency details
     *
     * @param dto {@link CommonProfileRequestDTO} - including language proficiency details
     * @return {@link CommonResponse} - language proficiency saved response
     * @author @Maleesha99
     */
    @Override
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("LanguageProficiencyServiceImpl.saveUpdate() => started.");

        CommonResponse response = new CommonResponse();
        List<LanguageProficiency> languageProficiencyList = new ArrayList<>();

        try {
            message = "Language proficiencies saved success.";

            log.info("Fetching candidate by id...");
            Candidate candidate = candidateRepository.findById(Integer.valueOf(dto.getIdCandidate()))
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));
            log.info("Candidate is fetched by id.");

            dto.getLanguageProficiencies().forEach(

                    languageProficiencyDTO -> {

                        log.info("Get language by id from communication service...");
                        LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(languageProficiencyDTO.getIdLanguage());
                        log.info("Language is fetched by id.");

                    });

            log.info("Saving language proficiency details...");
            languageProficiencyRepository.saveAll(languageProficiencyList);
            log.info("Language proficiency details are saved success.");

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "Language proficiencies already updated.";

            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "Language proficiencies created failed.";

            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);

        }

        log.info("LanguageProficiencyServiceImpl.saveUpdate() => ended.");
        return response;

    }

}

package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.schoolEducation.SchoolEducationMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.schoolEducation.SubjectHasSchoolEduGradeMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.Scheme;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.SchoolEducation;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.Subject;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.SubjectHasSchoolEduGrade;
import com.pdev.rempms.candidateservice.repository.SchemeRepository;
import com.pdev.rempms.candidateservice.repository.SubjectHasSchoolEduGradeRepository;
import com.pdev.rempms.candidateservice.repository.SubjectRepository;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.repository.candidate.SchoolEducationRepository;
import com.pdev.rempms.candidateservice.service.candidate.SchoolEducationService;
import com.pdev.rempms.candidateservice.service.rest.RestCommunicationInfoClientService;
import com.pdev.rempms.candidateservice.service.rest.RestLocationInfoClientService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import com.pdev.rempms.candidateservice.util.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/05
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolEducationServiceImpl implements SchoolEducationService {

    private final SchoolEducationRepository schoolEducationRepository;
    private final SchoolEducationMapper schoolEducationMapper;
    private final CandidateRepository candidateRepository;
    private final SchemeRepository schemeRepository;
    private final RestLocationInfoClientService restLocationInfoClientService;
    private final RestCommunicationInfoClientService restCommunicationInfoClientService;
    private final SubjectRepository subjectRepository;
    private final SubjectHasSchoolEduGradeMapper subjectHasSchoolEduGradeMapper;
    private final SubjectHasSchoolEduGradeRepository subjectHasSchoolEduGradeRepository;

    private String message;

    /**
     * This method is allowed to save school education details
     *
     * @param dto {@link CommonProfileRequestDTO} - including school education details
     * @return {@link CommonResponse} - school education saved response
     * @author @Maleesha99
     */
    @Override
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("SchoolEducationServiceImpl.saveUpdate() => started.");

        CommonResponse response = new CommonResponse();
        List<SchoolEducation> schoolEducationList = new ArrayList<>();
        List<SubjectHasSchoolEduGrade> subjectHasSchoolEduGrades = new ArrayList<>();

        try {
            message = "School education saved success.";

            log.info("Fetching candidate by id...");
            Candidate candidate = candidateRepository.findById(Integer.valueOf(dto.getIdCandidate()))
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));
            log.info("Candidate is fetched by id.");

            dto.getSchoolEducations().forEach(

                    schoolEducation -> {

                        log.info("Get country by id from location service...");
                        CountryDTO country = null;

                        log.info("Get language by id from communication service...");
                        LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(
                                Integer.valueOf(schoolEducation.getIdLanguage()));
                        log.info("Language is fetched by id.");

                        log.info("Mapping school education...");
                        SchoolEducation mappedSchoolEducation = schoolEducationMapper.toEntity(
                                new SchoolEducation(), schoolEducation, country, language, candidate);
                        log.info("School education is mapped.");

                        if (!CommonValidation.integerNullValidation(schoolEducation.getIdScheme())) {
                            log.info("Scheme is selected.");

                            log.info("Get scheme by id...");
                            Scheme scheme = schemeRepository.findById(schoolEducation.getIdScheme())
                                    .orElseThrow(() -> new RecordNotFoundException("Scheme not found!"));
                            log.info("Scheme is fetched by id.");

                            mappedSchoolEducation.setScheme(scheme);

                        }

                        schoolEducationList.add(mappedSchoolEducation);

                        schoolEducation.getSubjectHasSchoolEduGrades().forEach(

                                subjectHasSchoolEduGrade -> {

                                    log.info("Get subject by id...");
                                    Subject subject = subjectRepository.findById(subjectHasSchoolEduGrade.getIdSubject())
                                            .orElseThrow(() -> new RecordNotFoundException("Subject not found!"));
                                    log.info("Subject is fetched by id.");

                                    log.info("Mapping subject has school grade...");
                                    subjectHasSchoolEduGrades.add(subjectHasSchoolEduGradeMapper.toEntity(new SubjectHasSchoolEduGrade(),
                                            mappedSchoolEducation, subject, subjectHasSchoolEduGrade.getGrade()));
                                    log.info("Subject has school grade is mapped.");

                                }
                        );

                    }
            );

            log.info("Saving school education details...");
            schoolEducationRepository.saveAll(schoolEducationList);
            log.info("School education details are saved success.");

            log.info("Saving subject has grade details...");
            subjectHasSchoolEduGradeRepository.saveAll(subjectHasSchoolEduGrades);
            log.info("Subject has grade are saved success.");

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "School education already updated.";

            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "School education details created failed.";

            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);
        }

        log.info("SchoolEducationServiceImpl.saveUpdate() => ended.");
        return response;

    }

}

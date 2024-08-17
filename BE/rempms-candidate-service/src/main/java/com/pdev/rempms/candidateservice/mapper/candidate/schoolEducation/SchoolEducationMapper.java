package com.pdev.rempms.candidateservice.mapper.candidate.schoolEducation;

import com.pdev.rempms.candidateservice.dto.candidate.schoolEducation.SchoolEducationRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.schoolEducation.SchoolEducationResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.schoolEducation.SubjectHasSchoolEduGradeDTO;
import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.dto.scheme.SchemeDTO;
import com.pdev.rempms.candidateservice.mapper.scheme.SchemeMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.SchoolEducation;
import com.pdev.rempms.candidateservice.service.rest.RestCommunicationInfoClientService;
import com.pdev.rempms.candidateservice.service.rest.RestLocationInfoClientService;
import com.pdev.rempms.candidateservice.util.CommonValidation;
import com.pdev.rempms.candidateservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author @Maleesha99
 * @Date 2024/02/05
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SchoolEducationMapper {

    private final RestCommunicationInfoClientService restCommunicationInfoClientService;
    private final RestLocationInfoClientService restLocationInfoClientService;

    private final SchemeMapper schemeMapper;
    private final SubjectHasSchoolEduGradeMapper subjectHasSchoolEduGradeMapper;

    /**
     * This method is allowed to map school education dto to model
     *
     * @param schoolEducation {@link SchoolEducation} - school education model
     * @param dto             {@link SchoolEducationRequestDTO} - school education dto
     * @param country         {@link CountryDTO} -  country of school education
     * @param language        {@link LanguageDTO} -  medium of school education
     * @param candidate       {@link Candidate} -  candidate of school education
     * @return {@link SchoolEducation} - mapped model
     */
    public SchoolEducation toEntity(SchoolEducation schoolEducation, SchoolEducationRequestDTO dto, CountryDTO country,
                                    LanguageDTO language, Candidate candidate) {
        log.info("SchoolEducationMapper -> toEntity() => started!");

        if (!CommonValidation.integerNullValidation(dto.getIdSchoolEducation())) {
            log.info("SchoolEducationMapper -> toEntity() => School education exists!");

            schoolEducation.setId(dto.getIdSchoolEducation());
        }

        schoolEducation.setIdLanguage(language.getIdLanguage());
        schoolEducation.setIdCountry(country.getIdCountry());
        schoolEducation.setAchievedOn(DateTimeUtil.getFormattedDate(dto.getAchievedOn()));
        schoolEducation.setSchoolEduQualification(dto.getSchoolEduQualification());
        schoolEducation.setSchool(dto.getSchool());
        schoolEducation.setDescription(dto.getDescription());
        schoolEducation.setCandidate(candidate);

        log.info("SchoolEducationMapper -> toEntity() => ended!");
        return schoolEducation;
    }

    public SchoolEducationResponseDTO toDto(SchoolEducationResponseDTO dto,
                                            SchoolEducation schoolEducation) {
        log.info("SchoolEducationMapper -> toDto() => started!");

        dto.setIdSchoolEducation(schoolEducation.getId());
        dto.setSchool(schoolEducation.getSchool());
        dto.setDescription(schoolEducation.getDescription());
        dto.setAchievedOn(DateTimeUtil.getFormattedDate(schoolEducation.getAchievedOn()));
        dto.setSchoolEduQualification(schoolEducation.getSchoolEduQualification());

        dto.setSubjectHasSchoolEduGrades(schoolEducation.getSubjectHasSchoolEduGrades().isEmpty() ? new ArrayList<>() :
                schoolEducation.getSubjectHasSchoolEduGrades().stream().map(hasSchoolEduGrade ->
                        subjectHasSchoolEduGradeMapper.toDto(new SubjectHasSchoolEduGradeDTO(), hasSchoolEduGrade)).toList());

        dto.setIdScheme(schoolEducation.getScheme() == null ? null : schoolEducation.getScheme().getId());
        dto.setScheme(schoolEducation.getScheme() == null ? null :
                schemeMapper.toDto(new SchemeDTO(), schoolEducation.getScheme()));

        dto.setIdCountry(schoolEducation.getIdCountry());
        dto.setCountry(schoolEducation.getIdCountry() == null ? null :
                restLocationInfoClientService.getById(schoolEducation.getIdCountry()));

        dto.setIdLanguage(schoolEducation.getIdLanguage());
        dto.setLanguage(schoolEducation.getIdLanguage() == null ? null :
                restCommunicationInfoClientService.getActiveLanguageById(schoolEducation.getIdLanguage()));

        log.info("SchoolEducationMapper -> toDto() => ended!");
        return dto;

    }

}

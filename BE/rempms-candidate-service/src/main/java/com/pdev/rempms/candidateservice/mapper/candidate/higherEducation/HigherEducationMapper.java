package com.pdev.rempms.candidateservice.mapper.candidate.higherEducation;

import com.pdev.rempms.candidateservice.dto.candidate.areaOfStudy.AreaOfStudyDTO;
import com.pdev.rempms.candidateservice.dto.candidate.higherEducation.HigherEducationRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.higherEducation.HigherEducationResponseDTO;
import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.mapper.areaOfStudy.AreaOfStudyMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.AreaOfStudy;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEduQualification;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEducation;
import com.pdev.rempms.candidateservice.repository.AreaOfStudyRepository;
import com.pdev.rempms.candidateservice.service.rest.RestCommunicationInfoClientService;
import com.pdev.rempms.candidateservice.service.rest.RestLocationInfoClientService;
import com.pdev.rempms.candidateservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/04
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HigherEducationMapper {

    private final RestLocationInfoClientService restLocationInfoClientService;
    private final RestCommunicationInfoClientService restCommunicationInfoClientService;
    private final AreaOfStudyMapper areaOfStudyMapper;

    /**
     * This method is allowed to map higher education dto to model
     *
     * @param higherEducation        {@link HigherEducation} - higher education model
     * @param dto                    {@link HigherEducationRequestDTO} - higher education dto
     * @param higherEduQualification {@link HigherEduQualification} - higher education qualification
     * @param areaOfStudy            {@link AreaOfStudy} -  area of study of higher education
     * @param country                {@link CountryDTO} -  country of higher education
     * @param language               {@link LanguageDTO} -  medium of higher education
     * @param candidate              {@link Candidate} -  candidate of higher education
     * @return {@link HigherEducation} - mapped model
     */
    public HigherEducation toEntity(HigherEducation higherEducation, HigherEducationRequestDTO dto,
                                    HigherEduQualification higherEduQualification, AreaOfStudy areaOfStudy,
                                    CountryDTO country, LanguageDTO language, Candidate candidate) {
        log.info("HigherEducationMapper -> toEntity() => started!");

        higherEducation.setHigherEduQualification(higherEduQualification);
        higherEducation.setAreaOfStudy(areaOfStudy);
        higherEducation.setIdCountry(country.getIdCountry());
        higherEducation.setIdLanguage(language.getIdLanguage());
        higherEducation.setCandidate(candidate);
        higherEducation.setInstituteOfStudy(dto.getInstituteOfStudy());
        higherEducation.setAffiliatedInstitute(dto.getAffiliatedInstitute());
        higherEducation.setAwardType(dto.getAwardType());
        higherEducation.setCommencedDate(DateTimeUtil.getFormattedDate(dto.getCommencedDate()));
        higherEducation.setCompletionDate(DateTimeUtil.getFormattedDate(dto.getCompletionDate()));
        higherEducation.setStudentId(dto.getStudentId());
        higherEducation.setDescription(dto.getDescription());
        higherEducation.setResult(dto.getResult());

        log.info("HigherEducationMapper -> toEntity() => ended!");
        return higherEducation;
    }

    public HigherEducationResponseDTO toDto(HigherEducationResponseDTO dto,
                                            HigherEducation higherEducation) {
        log.info("HigherEducationMapper -> toDto() => started!");

        dto.setIdHigherEducation(higherEducation.getId());
        dto.setDescription(higherEducation.getDescription());
        dto.setResult(higherEducation.getResult());
        dto.setCommencedDate(DateTimeUtil.getFormattedDate(higherEducation.getCommencedDate()));
        dto.setCommencedDate(DateTimeUtil.getFormattedDate(higherEducation.getCommencedDate()));
        dto.setAwardType(higherEducation.getAwardType());
        dto.setAffiliatedInstitute(higherEducation.getAffiliatedInstitute());
        dto.setInstituteOfStudy(higherEducation.getInstituteOfStudy());
        dto.setStudentId(higherEducation.getStudentId());

        dto.setIdAreaOfStudy(higherEducation.getAreaOfStudy() == null ? null : higherEducation.getAreaOfStudy().getId());
        dto.setAreaOfStudy(higherEducation.getAreaOfStudy() == null ? null : areaOfStudyMapper.toDto(new AreaOfStudyDTO(), higherEducation.getAreaOfStudy()));

        dto.setIdCountry(higherEducation.getIdCountry());
        dto.setCountry(restLocationInfoClientService.getById(higherEducation.getIdCountry()));

        dto.setIdLanguage(higherEducation.getIdLanguage());
        dto.setLanguage(restCommunicationInfoClientService.getActiveLanguageById(higherEducation.getIdLanguage()));

        log.info("HigherEducationMapper -> toDto() => ended!");
        return dto;

    }

}

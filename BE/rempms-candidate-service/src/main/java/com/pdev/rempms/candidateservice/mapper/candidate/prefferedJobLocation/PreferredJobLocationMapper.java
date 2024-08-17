package com.pdev.rempms.candidateservice.mapper.candidate.prefferedJobLocation;

import com.pdev.rempms.candidateservice.dto.candidate.preferredJobLocation.PreferredJobLocationRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.preferredJobLocation.PreferredJobLocationResponseDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.prefferedJobLocation.PreferredJobLocation;
import com.pdev.rempms.candidateservice.service.rest.RestLocationInfoClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PreferredJobLocationMapper {

    private final RestLocationInfoClientService restLocationInfoClientService;

    /**
     * This method is allowed to convert model to dto
     *
     * @param preferredJobLocation {@link PreferredJobLocation} - entity
     * @return {@link PreferredJobLocationRequestDTO}
     * @author @maleeshasa
     */
    public PreferredJobLocationResponseDTO modelToDto(PreferredJobLocationResponseDTO dto, PreferredJobLocation preferredJobLocation) {

        dto.setIdPreferredJobLocation(preferredJobLocation.getId());

        // Country
        dto.setCountryId(preferredJobLocation.getCountryId());
        dto.setCountry(preferredJobLocation.getCountryId() == null ? null :
                restLocationInfoClientService.getById(preferredJobLocation.getCountryId()));

        return dto;

    }

    /**
     * This method is allowed to convert model to dto
     *
     * @param preferredJobLocationRequestDTO {@link PreferredJobLocationRequestDTO} - dto
     * @return {@link PreferredJobLocation}
     * @author @maleeshasa
     */
    public PreferredJobLocation dtoToModel(PreferredJobLocation preferredJobLocation, PreferredJobLocationRequestDTO preferredJobLocationRequestDTO,
                                           CountryDTO country, Candidate candidate) {
        log.info("PreferredJobLocationMapper -> dtoToModel() => started!");

        preferredJobLocation.setCountryId(country.getIdCountry());
        preferredJobLocation.setCandidate(candidate);
        preferredJobLocation.setActive(true);

        log.info("PreferredJobLocationMapper -> dtoToModel() => ended!");
        return preferredJobLocation;

    }

}

package com.pdev.rempms.candidateservice.service.rest;

import com.pdev.rempms.candidateservice.dto.candidate.personalDetail.PersonalDetailRequestDTO;
import com.pdev.rempms.candidateservice.dto.location.LocationInformationRequestDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
public interface RestLocationInfoClientService {

    /**
     * This method is allowed to save location details of candidate
     *
     * @param dto - {@link PersonalDetailRequestDTO} - personal details with location details
     * @return {@link LocationInformationRequestDTO} - saved location response
     * @author @Maleesha99
     */
    LocationInformationRequestDTO saveLocationInfo(PersonalDetailRequestDTO dto);

    /**
     * This method is allowed to get country by id
     *
     * @param idCountry {@link Integer} - country id
     * @return {@link CountryDTO} - country response
     * @author @Maleesha99
     */
    CountryDTO getById(Integer idCountry);

}

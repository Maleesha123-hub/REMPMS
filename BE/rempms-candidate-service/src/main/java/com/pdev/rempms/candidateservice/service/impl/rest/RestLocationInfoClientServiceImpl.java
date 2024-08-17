package com.pdev.rempms.candidateservice.service.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.rempms.candidateservice.client.LocationServiceClient;
import com.pdev.rempms.candidateservice.dto.candidate.personalDetail.PersonalDetailRequestDTO;
import com.pdev.rempms.candidateservice.dto.location.LocationInformationRequestDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.exception.BaseException;
import com.pdev.rempms.candidateservice.exception.FeignCustomException;
import com.pdev.rempms.candidateservice.mapper.localtion.LocationInformationMapper;
import com.pdev.rempms.candidateservice.service.rest.RestLocationInfoClientService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RestLocationInfoClientServiceImpl implements RestLocationInfoClientService {

    private final LocationServiceClient locationServiceClient;
    private final LocationInformationMapper locationInformationMapper;
    private final ObjectMapper objectMapper;

    /**
     * This method is allowed to save location details of candidate
     *
     * @param dto - {@link PersonalDetailRequestDTO} - personal details with location details
     * @return {@link LocationInformationRequestDTO} - saved location response
     */
    @Override
    public LocationInformationRequestDTO saveLocationInfo(PersonalDetailRequestDTO dto) {
        log.info("RestLocationInfoClientServiceImpl.saveLocationInfo() => started!");

        LocationInformationRequestDTO createdLocationInfo = new LocationInformationRequestDTO();
        LocationInformationRequestDTO locationInformation = locationInformationMapper.toDto(new LocationInformationRequestDTO(), dto);

        try {
            CommonResponse response = locationServiceClient.saveUpdateLocationInfo(locationInformation);
            createdLocationInfo = objectMapper.convertValue(response.getData(), LocationInformationRequestDTO.class);

        } catch (FeignCustomException ex) {
            log.warn("Save location info data for location service body : {} message : {} ", ex.getBody(), ex.getMessage());

        }

        log.info("RestLocationInfoClientServiceImpl.saveLocationInfo() => ended!");
        return createdLocationInfo;
    }

    /**
     * This method is allowed to get country by id
     *
     * @param idCountry {@link Integer} - country id
     * @return {@link CountryDTO} - country response
     * @author @Maleesha99
     */
    @Override
    public CountryDTO getById(Integer idCountry) {
        log.info("RestLocationInfoClientServiceImpl.saveLocationInfo() => started!");

        try {
            ResponseEntity<CommonResponse> response = locationServiceClient.getById(idCountry);

            if (response.getStatusCode().equals(HttpStatus.OK) && Objects.requireNonNull(response.getBody()).getData() != null) {

                return objectMapper.convertValue(response.getBody().getData(), CountryDTO.class);

            } else {
                log.warn("Country is not exists by id.");

                return null;

            }

        } catch (FeignCustomException ex) {
            log.warn("Save location info data for location service body : {} message : {} ", ex.getBody(), ex.getMessage());

            throw new BaseException(500, "Country is not exists.");

        }

    }

}

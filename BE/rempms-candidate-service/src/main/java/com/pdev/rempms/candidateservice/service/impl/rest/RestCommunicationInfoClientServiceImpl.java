package com.pdev.rempms.candidateservice.service.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.rempms.candidateservice.client.CommunicationServiceClient;
import com.pdev.rempms.candidateservice.dto.candidate.personalDetail.PersonalDetailRequestDTO;
import com.pdev.rempms.candidateservice.dto.communication.CommunicationInformationRequestDTO;
import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.dto.communication.preferredCommunication.PreferredCommunicationDTO;
import com.pdev.rempms.candidateservice.exception.BaseException;
import com.pdev.rempms.candidateservice.exception.FeignCustomException;
import com.pdev.rempms.candidateservice.mapper.communication.CommunicationInformationMapper;
import com.pdev.rempms.candidateservice.service.rest.RestCommunicationInfoClientService;
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
public class RestCommunicationInfoClientServiceImpl implements RestCommunicationInfoClientService {

    private final CommunicationServiceClient communicationServiceClient;
    private final CommunicationInformationMapper communicationInformationMapper;
    private final ObjectMapper objectMapper;

    /**
     * This method is allowed to save communication details of candidate
     *
     * @param dto - {@link PersonalDetailRequestDTO} - personal details with communication details
     * @return {@link CommunicationInformationRequestDTO} - saved communication details response
     */
    @Override
    public CommunicationInformationRequestDTO saveCommunicationInfo(PersonalDetailRequestDTO dto) {
        log.info("CommunicationInfoServiceImpl -> saveCommunicationInfo() => started!");

        CommunicationInformationRequestDTO communicationInformation = communicationInformationMapper.toDto(
                new CommunicationInformationRequestDTO(), dto);
        CommunicationInformationRequestDTO createdCommunicationInfo = new CommunicationInformationRequestDTO();

        try {
            CommonResponse response = communicationServiceClient.saveUpdate(communicationInformation);
            createdCommunicationInfo = objectMapper.convertValue(
                    response.getData(), CommunicationInformationRequestDTO.class);

        } catch (FeignCustomException ex) {
            log.warn("Save communication info data for communication service api call fail body : {} ", ex.getBody());

        }

        log.info("CommunicationInfoServiceImpl -> saveCommunicationInfo() => ended!");
        return createdCommunicationInfo;

    }

    /**
     * This method is allowed to get language by id
     *
     * @param idLanguage {@link Long} - language id
     * @return {@link LanguageDTO} - language response
     * @author @Maleesha99
     */
    @Override
    public LanguageDTO getActiveLanguageById(Integer idLanguage) {
        log.info("RestCommunicationInfoClientServiceImpl.getActiveLanguageById() => started!");

        try {
            ResponseEntity<CommonResponse> response = communicationServiceClient.getActiveLanguageById(idLanguage);

            if (response.getStatusCode().equals(HttpStatus.OK) && Objects.requireNonNull(response.getBody()).getData() != null) {
                log.info("Language is exists by id.");

                return objectMapper.convertValue(response.getBody().getData(), LanguageDTO.class);

            } else {
                log.info("Language is not exists by id.");

                return null;

            }

        } catch (FeignCustomException ex) {
            log.warn("Get language data from communication service message : {} body : {} ", ex.getMessage(), ex.getBody());

            throw new BaseException(500, "Language is not exists.");

        }

    }

    /**
     * This method is allowed to get preferred communication by id
     *
     * @param idPreferredCommunication {@link Long}
     * @return {@link PreferredCommunicationDTO} - preferred communication response
     * @author @Maleesha99
     */
    @Override
    public PreferredCommunicationDTO getActivePreferredCommunicationById(Integer idPreferredCommunication) {
        log.info("RestCommunicationInfoClientServiceImpl.getActivePreferredCommunicationById() => started!");

        try {
            ResponseEntity<CommonResponse> response = communicationServiceClient.getActivePreferredCommunicationById(idPreferredCommunication);

            if (response.getStatusCode().equals(HttpStatus.OK) && Objects.requireNonNull(response.getBody()).getData() != null) {
                log.info("Preferred communication is exists by id.");

                return objectMapper.convertValue(response.getBody().getData(), PreferredCommunicationDTO.class);

            } else {
                log.info("Preferred communication is not exists by id.");

                return null;

            }

        } catch (FeignCustomException ex) {
            log.warn("Get preferred communication data from communication service message : {} body : {} ", ex.getMessage(), ex.getBody());

        }

        log.info("RestCommunicationInfoClientServiceImpl.getActivePreferredCommunicationById() => ended!");

        return null;

    }

}

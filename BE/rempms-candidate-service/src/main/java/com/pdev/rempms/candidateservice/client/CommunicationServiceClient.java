package com.pdev.rempms.candidateservice.client;

import com.pdev.rempms.candidateservice.dto.communication.CommunicationInformationRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@FeignClient(name = "rempms-communication-service")
@Headers("Content-Type: application/json")
public interface CommunicationServiceClient {

    /**
     * This method is allowed to save communication details of candidate
     *
     * @param dto - {@link CommunicationInformationRequestDTO} - communication details
     * @return {@link CommonResponse} - saved communication response
     */
    @PostMapping(value = "/api/communication/v1/communicationInformation/saveUpdate")
    CommonResponse saveUpdate(CommunicationInformationRequestDTO dto);

    /**
     * This method is allowed to get language by id
     *
     * @param idLanguage {@link Long} - language id
     * @return {@link ResponseEntity<CommonResponse>} - language response
     * @author @Maleesha99
     */
    @GetMapping(value = "/api/communication/v1/language/getById/{idLanguage}")
    ResponseEntity<CommonResponse> getActiveLanguageById(@PathVariable Integer idLanguage);

    /**
     * This method is allowed to get preferred communication by id
     *
     * @param idPreferredCommunication {@link Long}
     * @return {@link ResponseEntity<CommonResponse>} - preferred communication response
     * @author @Maleesha99
     */
    @GetMapping(value = "/api/communication/v1/preferredCommunication/getById/{idPreferredCommunication}")
    ResponseEntity<CommonResponse> getActivePreferredCommunicationById(@PathVariable Integer idPreferredCommunication);

}

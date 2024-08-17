package com.pdev.rempms.candidateservice.client;

import com.pdev.rempms.candidateservice.dto.location.LocationInformationRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@FeignClient(name = "rempms-location-service")
@Headers("Content-Type: application/json")
public interface LocationServiceClient {

    /**
     * This method is allowed to save location details of candidate
     *
     * @param dto {@link LocationInformationRequestDTO} - location details
     * @return {@link CommonResponse} - saved location response
     * @author @Maleesha99
     */
    @PostMapping(value = "/api/v1/location/locationInformation/saveUpdate")
    CommonResponse saveUpdateLocationInfo(@RequestBody LocationInformationRequestDTO dto);

    /**
     * This method is allowed to get country by id
     *
     * @param idCountry {@link Long} - country id
     * @return {@link ResponseEntity<CommonResponse>} - country response
     * @author @Maleesha99
     */
    @GetMapping(value = "api/v1/location/country/getById/{idCountry}")
    ResponseEntity<CommonResponse> getById(@PathVariable Integer idCountry);

}

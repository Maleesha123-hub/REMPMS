package com.pdev.rempms.communicationservice.controller;

import com.pdev.rempms.communicationservice.dto.communicationInformation.CommunicationInformationDTO;
import com.pdev.rempms.communicationservice.service.communicationInformation.CommunicationInformationService;
import com.pdev.rempms.communicationservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/communication/v1/communicationInformation")
public class CommunicationInformationController {

    private final CommunicationInformationService communicationInformationService;

    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdateCommunicationInformation(@RequestBody CommunicationInformationDTO dto) {
        return communicationInformationService.saveUpdateCommunicationInformation(dto);
    }

    @GetMapping(value = "/getById/{idCommunicationInformation}")
    public ResponseEntity<CommonResponse> getActiveCommunicationInformationById(@PathVariable Long idCommunicationInformation) {
        return communicationInformationService.getActiveCommunicationInformationById(idCommunicationInformation);
    }

    @GetMapping(value ="/getAllActive")
    public ResponseEntity<CommonResponse> getAllActiveCommunicationInformation() {
        return communicationInformationService.getAllActiveCommunicationInformation();
    }

    @PostMapping(value ="/deleteById/{idCommunicationInformation}")
    public ResponseEntity<CommonResponse> deleteCommunicationInformationById(@PathVariable Long idCommunicationInformation){
        return communicationInformationService.deleteCommunicationInformationById(idCommunicationInformation);
    }
}

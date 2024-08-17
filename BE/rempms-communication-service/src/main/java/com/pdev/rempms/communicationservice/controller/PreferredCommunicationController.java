package com.pdev.rempms.communicationservice.controller;

import com.pdev.rempms.communicationservice.dto.preferredCommunication.PreferredCommunicationDTO;
import com.pdev.rempms.communicationservice.service.PreferredCommunicationService;
import com.pdev.rempms.communicationservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/communication/preferredCommunication")
public class PreferredCommunicationController {

    private final PreferredCommunicationService preferredCommunicationService;

    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdatePreferredCommunication(@RequestBody PreferredCommunicationDTO dto) {
        return preferredCommunicationService.saveUpdatePreferredCommunication(dto);
    }

    @GetMapping(value = "/getById/{idPreferredCommunication}")
    public ResponseEntity<CommonResponse> getActivePreferredCommunicationById(@PathVariable Long idPreferredCommunication) {
        return preferredCommunicationService.getActivePreferredCommunicationById(idPreferredCommunication);
    }

    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActivePreferredCommunications() {
        return preferredCommunicationService.getAllActivePreferredCommunications();
    }

    @GetMapping(value = "/deleteById/{idPreferredCommunication}")
    public ResponseEntity<CommonResponse> deletePreferredCommunicationById(@PathVariable Long idPreferredCommunication){
        return preferredCommunicationService.deletePreferredCommunicationById(idPreferredCommunication);
    }
}

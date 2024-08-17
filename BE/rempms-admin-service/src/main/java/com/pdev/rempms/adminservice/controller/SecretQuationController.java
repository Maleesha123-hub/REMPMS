package com.pdev.rempms.adminservice.controller;

import com.pdev.rempms.adminservice.dto.secretQuation.SecretQuationDTO;
import com.pdev.rempms.adminservice.service.secretQuation.SecretQuationService;
import com.pdev.rempms.adminservice.util.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author maleeshasa
 * @Date 29/12/2023
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/secret-quation")
public class SecretQuationController {

    private final SecretQuationService secretQuationService;

    /**
     * save or update secret quation
     *
     * @param dto - secret quation saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdate(@Valid @RequestBody SecretQuationDTO dto) {
        return secretQuationService.saveUpdate(dto);
    }

    /**
     * Get active secret quetion by id
     *
     * @param idSecretQuetion - secret quetion id
     * @return - Active secret quetion data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getById/{idSecretQuetion}")
    public ResponseEntity<CommonResponse> getActiveById(@PathVariable Long idSecretQuetion) {
        return secretQuationService.getActiveById(idSecretQuetion);
    }

    /**
     * Get all active secret quations
     *
     * @return - All Active secret quations data.
     * @author maleeshasa
     */
    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActive() {
        return secretQuationService.getAllActive();
    }

    /**
     * Delete secret quation by secret quation id
     *
     * @param idSecretQuetion - secret quation id
     * @return - secret quation deleted success info.
     * @author maleeshasa
     */
    @GetMapping(value = "/deleteById/{idSecretQuetion}")
    public ResponseEntity<CommonResponse> deleteById(@PathVariable Long idSecretQuetion) {
        return secretQuationService.deleteById(idSecretQuetion);
    }
}

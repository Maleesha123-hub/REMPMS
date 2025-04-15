package com.pdev.rempms.communicationservice.controller;

import com.pdev.rempms.communicationservice.dto.language.LanguageDTO;
import com.pdev.rempms.communicationservice.service.language.LanguageService;
import com.pdev.rempms.communicationservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/communication/v1/language")
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping(value = "/saveUpdate")
    public ResponseEntity<CommonResponse> saveUpdateLanguage(@RequestBody LanguageDTO dto) {
        return languageService.saveUpdateLanguage(dto);
    }

    @GetMapping(value = "/getById/{idLanguage}")
    public ResponseEntity<CommonResponse> getActiveLanguageById(@PathVariable Long idLanguage) {
        return languageService.getActiveLanguageById(idLanguage);
    }

    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActiveLanguages() {
        return languageService.getAllActiveLanguages();
    }

    @GetMapping(value = "/deleteById/{idLanguage}")
    public ResponseEntity<CommonResponse> deleteLanguageById(@PathVariable Long idLanguage){
        return languageService.deleteLanguageById(idLanguage);
    }
}

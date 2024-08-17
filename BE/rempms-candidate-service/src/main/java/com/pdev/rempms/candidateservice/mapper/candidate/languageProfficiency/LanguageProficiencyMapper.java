package com.pdev.rempms.candidateservice.mapper.candidate.languageProfficiency;

import com.pdev.rempms.candidateservice.dto.candidate.languageProficiency.LanguageProficiencyRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.languageProficiency.LanguageProficiencyResponseDTO;
import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.languageProfficiency.LanguageProficiency;
import com.pdev.rempms.candidateservice.service.rest.RestCommunicationInfoClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LanguageProficiencyMapper {

    private final RestCommunicationInfoClientService restCommunicationInfoClientService;

    public LanguageProficiency dtoToModel(LanguageProficiency languageProficiency, LanguageProficiencyRequestDTO languageProficiencyRequestDTO,
                                          LanguageDTO language, Candidate candidate) {
        log.info("LanguageProficiencyMapper.dtoToModel() => started.");

        languageProficiency.setIdLanguage(language.getIdLanguage());
        languageProficiency.setSpoken(languageProficiencyRequestDTO.getSpoken());
        languageProficiency.setReading(languageProficiencyRequestDTO.getReading());
        languageProficiency.setWriting(languageProficiencyRequestDTO.getWriting());
        languageProficiency.setCandidate(candidate);

        log.info("LanguageProficiencyMapper.dtoToModel() => ended.");
        return languageProficiency;

    }

    public LanguageProficiencyResponseDTO toDto(LanguageProficiencyResponseDTO dto, LanguageProficiency languageProficiency) {

        dto.setIdLanguageProficiency(languageProficiency.getId());
        dto.setLanguage(restCommunicationInfoClientService.getActiveLanguageById(languageProficiency.getIdLanguage()));
        dto.setReading(languageProficiency.getReading());
        dto.setWriting(languageProficiency.getWriting());
        dto.setSpoken(languageProficiency.getSpoken());

        return dto;

    }

}

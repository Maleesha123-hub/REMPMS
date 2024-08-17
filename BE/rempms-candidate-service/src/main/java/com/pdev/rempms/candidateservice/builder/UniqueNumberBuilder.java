package com.pdev.rempms.candidateservice.builder;

import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UniqueNumberBuilder {

    private final CandidateRepository candidateRepository;

    /**
     * This method is allowed to generate next reference no
     *
     * @param make
     * @param model
     * @return {@link String} - reference no
     */
    public String generateNextReference(String make, String model) {
        log.info("UniqueNumberBuilder.generateNextReference >> started.");

        StringBuilder nextNoStr = new StringBuilder(make + "-" + model);

        Optional<Candidate> candidate = candidateRepository.findTopByOrderByIdDesc();

        if (candidate.isEmpty()) {
            log.info("Candidate is empty.");
            nextNoStr.append("000001");

        } else {
            log.info("Candidate is available for top order desc.");

            Integer currentNo = candidate.get().getId();
            nextNoStr.append(String.format("%06d", currentNo));

        }

        log.info("UniqueNumberBuilder.generateNextReference >> ended.");
        return nextNoStr.toString();
    }
}

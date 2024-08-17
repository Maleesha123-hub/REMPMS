package com.pdev.rempms.draftservice.repository;

import com.pdev.rempms.draftservice.model.CandidateCommonProfileDraft;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author @Maleesha99
 * @Date 2024/03/01
 */
public interface CandidateCommonProfileDraftRepository extends MongoRepository<CandidateCommonProfileDraft, Long> {

    /**
     * find by candidate id
     *
     * @param idCandidate
     * @return Optional<CandidateCommonProfileDraft>
     */
    Optional<CandidateCommonProfileDraft> findByIdCandidate(Integer idCandidate);

    Optional<CandidateCommonProfileDraft> findTopByOrderByIdDesc();

}

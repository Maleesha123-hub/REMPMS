package com.pdev.rempms.draftservice.builder;

import com.pdev.rempms.draftservice.model.CandidateCommonProfileDraft;
import com.pdev.rempms.draftservice.model.DatabaseSequence;
import com.pdev.rempms.draftservice.repository.CandidateCommonProfileDraftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author @Maleesha99
 * @Date 2024/03/01
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SequenceBuilder {

    private final MongoOperations mongoOperations;
    private final CandidateCommonProfileDraftRepository candidateCommonProfileDraftRepository;

    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                DatabaseSequence.class
        );

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    public String generateNextReference(String make, String model) {
        log.info("UniqueNumberBuilder.generateNextReference >> started.");

        StringBuilder nextNoStr = new StringBuilder(make + "-" + model);

        Optional<CandidateCommonProfileDraft> profileDraft = candidateCommonProfileDraftRepository.findTopByOrderByIdDesc();

        if (profileDraft.isEmpty()) {
            log.info("Draft is empty.");
            nextNoStr.append("000001");

        } else {
            log.info("Draft is available for top order desc.");

            Long currentNo = profileDraft.get().getId();
            nextNoStr.append(String.format("%06d", currentNo));

        }

        log.info("UniqueNumberBuilder.generateNextReference >> ended.");

        return nextNoStr.toString();

    }

}

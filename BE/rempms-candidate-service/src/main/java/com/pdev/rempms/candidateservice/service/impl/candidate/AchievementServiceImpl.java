package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.achievement.AchievementMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.repository.candidate.AchievementsRepository;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.service.candidate.AchievementService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

/**
 * @author @Maleesha99
 * @Date 2024/02/09
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AchievementServiceImpl implements AchievementService {

    private final CandidateRepository candidateRepository;
    private final AchievementsRepository achievementsRepository;
    private final AchievementMapper achievementMapper;
    private String message;

    /**
     * This method is allowed to save or update achievements
     *
     * @param dto {@link CommonProfileRequestDTO} - including achievements details
     * @return {@link CommonResponse} - achievements saved response
     * @author @Maleesha99
     */
    @Override
    @Transactional
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("AchievementServiceImpl.saveUpdate() => started.");

        CommonResponse response = new CommonResponse();

        try {
            message = "Achievements saved success.";

            log.info("Fetching candidate by id...");
            Candidate candidate = candidateRepository.findById(Integer.valueOf(dto.getIdCandidate()))
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));
            log.info("Candidate is fetched by id.");

//            Achievement achievement = achievementMapper.dtoToModel(dto.getAchievements());
//            achievement.setCandidate(candidate);
//
//            log.info("Saving achievements details...");
//            achievementsRepository.save(achievement);
            log.info("Achievements details are saved success.");

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "Achievements already updated.";

            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "Achievements details created failed.";

            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);

        }

        log.info("AchievementServiceImpl.saveUpdate() => ended.");
        return response;

    }

}

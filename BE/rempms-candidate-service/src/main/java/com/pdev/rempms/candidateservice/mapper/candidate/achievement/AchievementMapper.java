package com.pdev.rempms.candidateservice.mapper.candidate.achievement;

import com.pdev.rempms.candidateservice.dto.candidate.achievments.AchievementDTO;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.achievement.Achievement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/09
 */
@Component
@Slf4j
public class AchievementMapper {

    /**
     * This method is allowed to convert model to dto
     *
     * @param dto         {@link AchievementDTO} - dto
     * @param achievement {@link Achievement} - dto
     * @return {@link AchievementDTO}
     * @author @maleeshasa
     */
    public AchievementDTO modelToDto(AchievementDTO dto, Achievement achievement) {
        log.info("AchievementMapper -> modelToDto() => started!");

        dto.setIdAchievement(achievement.getId());
        dto.setAchievements(achievement.getAchievements());

        log.info("AchievementMapper -> modelToDto() => ended!");
        return dto;

    }

    /**
     * This method is allowed to convert model to dto
     *
     * @param achievementDTO {@link AchievementDTO} - dto
     * @return {@link Achievement}
     * @author @maleeshasa
     */
    public Achievement dtoToModel(Achievement achievement, AchievementDTO achievementDTO, Candidate candidate) {
        log.info("AchievementMapper -> dtoToModel() => started!");

        achievement.setAchievements(achievementDTO.getAchievements());
        achievement.setCandidate(candidate);

        log.info("AchievementMapper -> dtoToModel() => ended!");
        return achievement;

    }

}

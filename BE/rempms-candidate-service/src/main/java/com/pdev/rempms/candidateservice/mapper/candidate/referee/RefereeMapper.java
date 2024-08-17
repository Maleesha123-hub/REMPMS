package com.pdev.rempms.candidateservice.mapper.candidate.referee;

import com.pdev.rempms.candidateservice.dto.candidate.referee.RefereeDTO;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.referee.Referee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
@Component
@Slf4j
public class RefereeMapper {

    /**
     * This method is allowed to convert model to dto
     *
     * @param referee {@link Referee} - entity
     * @return {@link RefereeDTO}
     * @author @maleeshasa
     */
    public RefereeDTO modelToDto(RefereeDTO dto, Referee referee) {

        dto.setIdReferee(referee.getId());
        dto.setName(referee.getName());
        dto.setPhone(referee.getPhone());
        dto.setEmail(referee.getEmail());
        dto.setAddress(referee.getAddress());
        dto.setDesignation(referee.getDesignation());
        dto.setRelationship(referee.getRelationship());
        return dto;

    }

    /**
     * This method is allowed to convert model to dto
     *
     * @param refereeDTO {@link RefereeDTO} - dto
     * @return {@link Referee}
     * @author @maleeshasa
     */
    public Referee dtoToModel(Referee referee, RefereeDTO refereeDTO, Candidate candidate) {
        log.info("RefereeMapper.dtoToModel() => started.");

        referee.setEmail(refereeDTO.getEmail());
        referee.setAddress(refereeDTO.getAddress());
        referee.setName(refereeDTO.getName());
        referee.setPhone(refereeDTO.getPhone());
        referee.setDesignation(refereeDTO.getDesignation());
        referee.setRelationship(refereeDTO.getRelationship());
        referee.setCandidate(candidate);

        log.info("RefereeMapper.dtoToModel() => ended.");
        return referee;

    }

}

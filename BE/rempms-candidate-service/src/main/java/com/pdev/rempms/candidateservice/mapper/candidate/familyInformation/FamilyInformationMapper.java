package com.pdev.rempms.candidateservice.mapper.candidate.familyInformation;

import com.pdev.rempms.candidateservice.dto.candidate.familyInformation.FamilyInformationDTO;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.familyInformation.FamilyInformation;
import com.pdev.rempms.candidateservice.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
@Component
@Slf4j
public class FamilyInformationMapper {

    /**
     * This method is allowed to convert model to dto
     *
     * @param familyInformation {@link FamilyInformation} - entity
     * @return {@link FamilyInformationDTO}
     * @author @maleeshasa
     */
    public FamilyInformationDTO modelToDto(FamilyInformationDTO dto, FamilyInformation familyInformation) {

        dto.setIdFamilyInformation(familyInformation.getId());
        dto.setName(familyInformation.getName());
        dto.setGender(familyInformation.getGender());
        dto.setDob(DateTimeUtil.getFormattedDate(familyInformation.getDob()));
        dto.setDesignation(familyInformation.getDesignation());
        dto.setRemark(familyInformation.getRemark());
        dto.setRelationship(familyInformation.getRelationship());
        dto.setIsDependant(familyInformation.getIsDependant());
        dto.setSchoolOrOrganization(familyInformation.getSchoolOrOrganization());

        return dto;

    }

    /**
     * This method is allowed to convert model to dto
     *
     * @param familyInformationDTO {@link FamilyInformationDTO} - dto
     * @return {@link FamilyInformation}
     * @author @maleeshasa
     */
    public FamilyInformation dtoToModel(FamilyInformation familyInformation, FamilyInformationDTO familyInformationDTO, Candidate candidate) {
        log.info("FamilyInformationMapper.dtoToModel() => started.");

        familyInformation.setName(familyInformationDTO.getName());
        familyInformation.setDesignation(familyInformationDTO.getDesignation());
        familyInformation.setGender(familyInformationDTO.getGender());
        familyInformation.setDob(DateTimeUtil.getFormattedDate(familyInformationDTO.getDob()));
        familyInformation.setRelationship(familyInformationDTO.getRelationship());
        familyInformation.setSchoolOrOrganization(familyInformationDTO.getSchoolOrOrganization());
        familyInformation.setIsDependant(familyInformationDTO.getIsDependant());
        familyInformation.setRemark(familyInformationDTO.getRemark());
        familyInformation.setCandidate(candidate);

        log.info("FamilyInformationMapper.dtoToModel() => ended.");
        return familyInformation;

    }

}

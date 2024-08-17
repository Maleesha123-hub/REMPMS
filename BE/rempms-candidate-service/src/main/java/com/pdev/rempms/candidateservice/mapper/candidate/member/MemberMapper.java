package com.pdev.rempms.candidateservice.mapper.candidate.member;

import com.pdev.rempms.candidateservice.dto.candidate.membership.MembershipRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.membership.MembershipResponseDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.dto.membershipType.MembershipTypeDTO;
import com.pdev.rempms.candidateservice.mapper.membershipType.MembershipTypeMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.member.Membership;
import com.pdev.rempms.candidateservice.model.candidate.member.MembershipType;
import com.pdev.rempms.candidateservice.service.rest.RestLocationInfoClientService;
import com.pdev.rempms.candidateservice.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author @Maleesha99
 * @Date 2024/02/05
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MemberMapper {

    private final MembershipTypeMapper membershipTypeMapper;
    private final RestLocationInfoClientService restLocationInfoClientService;

    /**
     * This method is allowed to map membership model
     *
     * @param membership     {@link Membership} - membership model
     * @param dto            {@link MembershipRequestDTO} - membership dto
     * @param country        {@link CountryDTO} - country dto
     * @param membershipType {@link MembershipType} - membership type
     * @param candidate      {@link Candidate} - candidate of that membership
     * @return {@link Membership} - mapped model
     * @author @Maleesha99
     */
    public Membership toEntity(Membership membership, MembershipRequestDTO dto, CountryDTO country,
                               MembershipType membershipType, Candidate candidate) {
        log.info("MemberMapper -> toEntity() => started!");

        membership.setMembershipType(membershipType);
        membership.setIdCountry(country.getIdCountry());
        membership.setDescription(dto.getDescription());
        membership.setCandidate(candidate);
        membership.setYearObtained(DateTimeUtil.getFormattedDate(dto.getYearObtained()));

        log.info("MemberMapper -> toEntity() => ended!");
        return membership;
    }

    public MembershipResponseDTO toDto(MembershipResponseDTO dto,
                                       Membership membership) {
        log.info("MemberMapper -> toDto() => started!");

        dto.setIdMembership(membership.getId());
        dto.setDescription(membership.getDescription());
        dto.setYearObtained(DateTimeUtil.getFormattedDate(membership.getYearObtained()));

        // Membership type
        dto.setIdMembershipType(membership.getMembershipType() == null ? null :
                membership.getMembershipType().getId());
        dto.setMembershipType(membership.getMembershipType() == null ? null :
                membershipTypeMapper.toDto(new MembershipTypeDTO(), membership.getMembershipType()));

        dto.setIdCountry(membership.getIdCountry());
        dto.setCountry(restLocationInfoClientService.getById(membership.getIdCountry()));

        log.info("MemberMapper -> toDto() => ended!");
        return dto;

    }

}

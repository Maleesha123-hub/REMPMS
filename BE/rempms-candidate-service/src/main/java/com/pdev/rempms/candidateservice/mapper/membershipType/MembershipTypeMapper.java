package com.pdev.rempms.candidateservice.mapper.membershipType;

import com.pdev.rempms.candidateservice.dto.membershipType.MembershipTypeDTO;
import com.pdev.rempms.candidateservice.model.candidate.member.MembershipType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MembershipTypeMapper {

    public MembershipTypeDTO toDto(MembershipTypeDTO dto, MembershipType membershipType) {

        dto.setId(membershipType.getId());
        dto.setDescription(membershipType.getDescription());
        dto.setName(membershipType.getName());

        return dto;

    }

}

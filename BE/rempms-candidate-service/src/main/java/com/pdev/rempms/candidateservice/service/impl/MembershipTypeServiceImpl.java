package com.pdev.rempms.candidateservice.service.impl;

import com.pdev.rempms.candidateservice.dto.membershipType.MembershipTypeDTO;
import com.pdev.rempms.candidateservice.mapper.membershipType.MembershipTypeMapper;
import com.pdev.rempms.candidateservice.model.candidate.member.MembershipType;
import com.pdev.rempms.candidateservice.repository.MembershipTypeRepository;
import com.pdev.rempms.candidateservice.service.MembershipTypeService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipTypeServiceImpl implements MembershipTypeService {

    private final MembershipTypeRepository membershipTypeRepository;
    private final MembershipTypeMapper membershipTypeMapper;

    @Override
    public CommonResponse getAllActive() {

        CommonResponse response = new CommonResponse();

        List<MembershipType> membershipTypes = membershipTypeRepository.findAll();

        if (!membershipTypes.isEmpty()) {

            List<MembershipTypeDTO> membershipTypeDTOS = membershipTypes.stream()
                    .map(membershipType -> membershipTypeMapper.toDto(new MembershipTypeDTO(), membershipType)).toList();

            response.setMessage("Membership types are exists.");
            response.setStatus(HttpStatus.OK);
            response.setData(membershipTypeDTOS);

        } else {

            response.setMessage("Membership types are not exists.");
            response.setStatus(HttpStatus.OK);
            response.setData(null);

        }

        return response;

    }

}

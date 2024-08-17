package com.pdev.rempms.candidateservice.mapper.candidate.personalDetail;

import com.pdev.rempms.candidateservice.dto.candidate.personalDetail.PersonalDetailRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.personalDetail.PersonalDetailResponseDTO;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.personalDetail.PersonalDetail;
import com.pdev.rempms.candidateservice.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Component
@Slf4j
public class PersonalDetailMapper {

    public PersonalDetail toEntity(PersonalDetail personalDetail, Candidate candidate, Integer idCommunicationInformation,
                                   Integer idLocationInformation, PersonalDetailRequestDTO dto) {
        log.info("PersonalDetailMapper -> toEntity() => started!");

        personalDetail.setDob(DateTimeUtil.getFormattedDate(dto.getDob()));
        personalDetail.setGender(dto.getGender());
        personalDetail.setInitial(dto.getInitial());
        personalDetail.setExpectedSalary(BigDecimal.valueOf(Double.parseDouble(dto.getExpectedSalary())));
        personalDetail.setFirstName(dto.getFirstName());
        personalDetail.setLastName(dto.getLastName());
        personalDetail.setMaritalStatus(dto.getMaritalStatus());
        personalDetail.setNic(dto.getNic());
        personalDetail.setPassportNo(dto.getPassportNo());
        personalDetail.setNoticePeriod(dto.getNoticePeriod());
        personalDetail.setSalutation(dto.getSalutation());
        personalDetail.setIdCommunicationInformation(idCommunicationInformation);
        personalDetail.setIdLocationInformation(idLocationInformation);
        personalDetail.setCandidate(candidate);

        log.info("PersonalDetailMapper -> toEntity() => ended!");
        return personalDetail;
    }

    public PersonalDetailResponseDTO toDto(PersonalDetailResponseDTO dto,
                                           PersonalDetail personalDetail) {
        log.info("PersonalDetailMapper -> toDto() => started!");

        dto.setIdPersonalDetail(personalDetail.getId());
        dto.setSalutation(personalDetail.getSalutation());
        dto.setFirstName(personalDetail.getFirstName());
        dto.setLastName(personalDetail.getLastName());
        dto.setInitial(personalDetail.getInitial());
        dto.setGender(personalDetail.getGender());
        dto.setDob(DateTimeUtil.getFormattedDate(personalDetail.getDob()));
        dto.setMaritalStatus(personalDetail.getMaritalStatus());
        dto.setNic(personalDetail.getNic());
        dto.setPassportNo(personalDetail.getPassportNo());
        dto.setNic(personalDetail.getNic());
        dto.setExpectedSalary(String.valueOf(personalDetail.getExpectedSalary()));
        dto.setNoticePeriod(personalDetail.getNoticePeriod());

        //location-service
        dto.setIdLocationInformation(personalDetail.getIdLocationInformation());
        dto.setLocationInformation(null); //TODO

        //communication-service
        dto.setIdCommunicationInformation(personalDetail.getIdCommunicationInformation());
        dto.setCommunicationInformation(null); //TODO

        log.info("PersonalDetailMapper -> toDto() => ended!");
        return dto;

    }

}

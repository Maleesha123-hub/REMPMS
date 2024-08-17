package com.pdev.rempms.candidateservice.dto.candidate.membership;

import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.dto.membershipType.MembershipTypeDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class MembershipResponseDTO {

    private Integer idMembership;

    private Integer idMembershipType;
    private MembershipTypeDTO membershipType;

    private Integer idCountry; //location-service
    private CountryDTO country;

    private String yearObtained; // year
    private String description;

}

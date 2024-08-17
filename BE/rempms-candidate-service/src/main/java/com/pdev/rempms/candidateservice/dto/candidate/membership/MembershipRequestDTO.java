package com.pdev.rempms.candidateservice.dto.candidate.membership;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class MembershipRequestDTO {

    private Integer idMembership;
    private Integer idMembershipType;
    private Integer idCountry; //location-service
    private String yearObtained; // year
    private String description;

}

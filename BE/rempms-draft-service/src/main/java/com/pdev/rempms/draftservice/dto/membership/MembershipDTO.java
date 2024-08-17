package com.pdev.rempms.draftservice.dto.membership;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class MembershipDTO {

    private String idMembership;
    private String idMembershipType;
    private String idCountry; //location-service
    private String yearObtained; // year
    private String description;

}

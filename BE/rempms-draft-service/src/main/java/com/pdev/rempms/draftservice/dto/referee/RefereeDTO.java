package com.pdev.rempms.draftservice.dto.referee;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class RefereeDTO {
    private String idReferee;
    private String name;
    private String relationship;
    private String designation;
    private String phone;
    private String email;
    private String address;
    private String idCandidate;
}

package com.pdev.rempms.draftservice.dto.familyInformation;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class FamilyInformationDTO {

    private String idFamilyInformation;
    private String name;
    private String relationship;
    private String dob;
    private String gender; // enum : gender
    private String designation;
    private String schoolOrOrganization;
    private String remark;
    private Boolean isDependant;
    private String idCandidate;
}

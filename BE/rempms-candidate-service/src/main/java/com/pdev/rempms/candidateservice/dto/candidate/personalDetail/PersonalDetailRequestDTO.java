package com.pdev.rempms.candidateservice.dto.candidate.personalDetail;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class PersonalDetailRequestDTO {
    private Integer idPersonalDetail;
    private String salutation; //enum
    private String firstName;
    private String lastName;
    private String initial;
    private String gender; //enum
    private String dob;
    private String maritalStatus; //enum
    private String nic;
    private String passportNo;
    private String expectedSalary;
    private String noticePeriod; //enum

    //location-service
    private String no;
    private String streetNo1;
    private String streetNo2;
    private Integer idDistrict;
    private Integer idProvince;
    private Integer idCity;
    private Integer idCountry;
    private Integer idLocationInformation; //location-service

    //communication-service
    private String phoneNo;
    private String mobileNo;
    private String email;
    private Integer idLanguage;
    private Integer idPreferredLanguage;
    private Integer idCommunicationInformation; //communication-service

}

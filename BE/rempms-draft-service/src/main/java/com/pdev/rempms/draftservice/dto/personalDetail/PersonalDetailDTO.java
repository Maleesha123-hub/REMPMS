package com.pdev.rempms.draftservice.dto.personalDetail;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class PersonalDetailDTO {
    private String idPersonalDetail;
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
    private String idDistrict;
    private String idProvince;
    private String idCity;
    private String idCountry;
    private String idLocationInformation; //location-service

    //communication-service
    private String phoneNo;
    private String mobileNo;
    private String email;
    private String idLanguage;
    private String idPreferredLanguage;
    private String idCommunicationInformation; //communication-service

}

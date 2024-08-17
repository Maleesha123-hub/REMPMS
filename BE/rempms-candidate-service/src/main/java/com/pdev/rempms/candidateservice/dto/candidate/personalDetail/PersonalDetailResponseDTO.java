package com.pdev.rempms.candidateservice.dto.candidate.personalDetail;

import com.pdev.rempms.candidateservice.dto.communication.CommunicationInformationResponseDTO;
import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.dto.communication.preferredCommunication.PreferredCommunicationDTO;
import com.pdev.rempms.candidateservice.dto.location.LocationInformationResponseDTO;
import com.pdev.rempms.candidateservice.dto.location.city.CityDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.dto.location.district.DistrictDTO;
import com.pdev.rempms.candidateservice.dto.location.province.ProvinceDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class PersonalDetailResponseDTO {
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
    private Integer idLocationInformation;
    private LocationInformationResponseDTO locationInformation;

    //communication-service
    private Integer idCommunicationInformation; //communication-service
    private CommunicationInformationResponseDTO communicationInformation;

}

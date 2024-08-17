package com.pdev.rempms.candidateservice.dto.location;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class LocationInformationRequestDTO { //location-service
    private String no;
    private String streetNo1;
    private String streetNo2;
    private Integer idDistrict;
    private Integer idProvince;
    private Integer idCity;
    private Integer idCountry;
    private Integer idLocationInformation;
}

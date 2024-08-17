package com.pdev.rempms.candidateservice.dto.location.country;

import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha
 * @Date 2024/02/04
 */
@Getter
@Setter
public class CountryDTO {

    private Integer idCountry;
    private String countryName;
    private String countryCode;
    private String nationality;
    private String commonStatus;

}

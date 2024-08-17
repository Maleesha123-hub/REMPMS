package com.pdev.rempms.candidateservice.dto.candidate.preferredJobLocation;

import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class PreferredJobLocationResponseDTO {

    private Integer idPreferredJobLocation;

    private Integer countryId;
    private CountryDTO country;

}

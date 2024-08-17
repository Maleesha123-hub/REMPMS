package com.pdev.rempms.draftservice.dto.preferredJobLocation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
public class PreferredJobLocationDTO {

    private String idPreferredJobLocation;
    private String idCandidate;
    private List<Integer> idCountries;

}

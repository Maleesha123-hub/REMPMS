package com.pdev.rempms.recruitmentservice.dto.employer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployerSavedLazyResponseDTO {
    private Integer id;
    private String employerName;
    private String address;
    private String telephoneNo;
    private String mobileNo;
    private Boolean active;
}

package com.pdev.rempms.recruitmentservice.dto.employer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployerDTO {

    private Integer id;
    private String employerName;
    private String address;
    private String telephoneNo;
    private String mobileNo;
    private String employerNo;
    private Boolean active;

}

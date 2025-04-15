package com.pdev.rempms.candidateservice.builder.samples;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravelAgentSearchDTO {
    private String travelAgentName;
    private String brNumber;
    private String fromDate;
    private String toDate;
    private int pageNo;
    private int size;
}


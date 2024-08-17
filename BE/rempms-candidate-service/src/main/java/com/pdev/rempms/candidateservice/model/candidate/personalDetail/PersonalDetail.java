package com.pdev.rempms.candidateservice.model.candidate.personalDetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pdev.rempms.candidateservice.model.AuditData;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author maleeshasa
 * @Date 31/12/2023
 */
@Entity
@Getter
@Setter
@Table(name = "personal_detail")
public class PersonalDetail {
    @Id
    @Column(name = "id_personal_detail")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "salutation")
    private String salutation; //enum

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "initial")
    private String initial;

    @Column(name = "gender")
    private String gender; //enum

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "marital_status")
    private String maritalStatus; //enum

    @Column(name = "nic")
    private String nic;

    @Column(name = "passport_no")
    private String passportNo;

    @Column(name = "expected_salary")
    private BigDecimal expectedSalary;

    @Column(name = "notice_period")
    private String noticePeriod; //enum

    @Column(name = "location_information_id_location_information")
    private Integer IdLocationInformation; //location-service

    @Column(name = "communication_information_id_communication_information")
    private Integer idCommunicationInformation; //communication-service

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

    @JoinColumn(name = "candidate_id_candidate")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Candidate candidate;

}

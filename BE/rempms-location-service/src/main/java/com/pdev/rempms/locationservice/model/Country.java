package com.pdev.rempms.locationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Data
@Table(name = "country")
public class Country {
    @Id
    @Column(name = "id_country")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_name")
    @Length(max = 45)
    private String countryName;

    @Column(name = "country_code")
    private Integer countryCode;

    @Column(name = "nationality")
    @Length(max = 45)
    private String nationality;

    @Column(name = "common_status")
    @Length(max = 45)
    private String commonStatus;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "createdBy", column = @Column(name = "created_by")),
            @AttributeOverride(name = "createdOn", column = @Column(name = "created_on")),
            @AttributeOverride(name = "updatedBy", column = @Column(name = "updated_by")),
            @AttributeOverride(name = "updatedOn", column = @Column(name = "updated_on"))
    })
    private AuditData auditData;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<Province> provinceList;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private List<LocationInformation> locationInformationList;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private List<District> districtList;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private List<City> cityList;
}

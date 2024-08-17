package com.pdev.rempms.locationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Data
@Table(name = "province")
public class Province {
    @Id
    @Column(name = "id_province")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "province_name")
    @Length(max = 45)
    private String provinceName;

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

    @JoinColumn(name = "country_id_country")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Country country;

    @OneToMany(mappedBy = "province", fetch = FetchType.EAGER)
    private List<District> districtList;

    @OneToMany(mappedBy = "province")
    @JsonIgnore
    private List<LocationInformation> locationInformationList;

    @OneToMany(mappedBy = "province")
    @JsonIgnore
    private List<City> cityList;
}

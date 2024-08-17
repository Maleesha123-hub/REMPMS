package com.pdev.rempms.locationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Data
@Table(name = "district")
public class District {
    @Id
    @Column(name = "id_district")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district_name")
    @Length(max = 45)
    private String districtName;

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

    @JoinColumn(name = "province_id_province")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Province province;

    @JoinColumn(name = "country_id_country")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Country country;

    @OneToMany(mappedBy = "district", fetch = FetchType.EAGER)
    private List<City> cityList;

    @OneToMany(mappedBy = "district")
    @JsonIgnore
    private List<LocationInformation> locationInformationList;
}

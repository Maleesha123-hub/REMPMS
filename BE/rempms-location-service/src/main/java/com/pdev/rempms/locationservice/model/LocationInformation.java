package com.pdev.rempms.locationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@Table(name = "location_information")
public class LocationInformation {
    @Id
    @Column(name = "id_location_information")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no", nullable = false)
    @Length(max = 45)
    private String no;

    @Column(name = "street_no1", nullable = false)
    @Length(max = 45)
    private String streetNo1;

    @Column(name = "street_no2", nullable = false)
    @Length(max = 45)
    private String streetNo2;

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

    @JoinColumn(name = "city_id_city")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private City city;

    @JoinColumn(name = "district_id_district")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private District district;

    @JoinColumn(name = "province_id_province")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Province province;

    @JoinColumn(name = "country_id_country")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Country country;
}

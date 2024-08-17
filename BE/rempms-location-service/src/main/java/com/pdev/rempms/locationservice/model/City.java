package com.pdev.rempms.locationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Data
@Table(name = "city")
public class City {
    @Id
    @Column(name = "id_city")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name")
    @Length(max = 45)
    private String cityName;

    @Column(name = "city_zip_code")
    private Integer cityZipCode;

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

    @JoinColumn(name = "province_id_province")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Province province;

    @JoinColumn(name = "district_id_district")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private District district;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<LocationInformation> locationInformationList;
}

package com.pdev.rempms.communicationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Data
@Table(name = "language")
public class Language {
    @Id
    @Column(name = "id_language")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "language_name")
    @Length(max = 45)
    private String languageName;

    @Column(name = "language_description")
    @Length(max = 45)
    private String languageDescription;

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

    @OneToMany(mappedBy = "language", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CommunicationInformation> communicationInformationList;
}

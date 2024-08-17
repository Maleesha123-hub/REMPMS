package com.pdev.rempms.communicationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Data
@Table(name = "preferred_ommunication")
public class PreferredCommunication {
    @Id
    @Column(name = "id_preferred_communication")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "preferredCommunication_method")
    @Length(max = 45)
    private String preferredCommunicationMethod;

    @Column(name = "preferred_communication_description")
    @Length(max = 45)
    private String preferredCommunicationDescription;

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

    @OneToMany(mappedBy = "preferredCommunication", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CommunicationInformation> communicationInformationList;
}

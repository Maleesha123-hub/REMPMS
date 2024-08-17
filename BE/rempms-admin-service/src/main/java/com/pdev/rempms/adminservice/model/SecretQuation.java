package com.pdev.rempms.adminservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 29/12/2023
 */
@Entity
@Data
@Table(name = "secret_quation")
public class SecretQuation {
    @Id
    @Column(name = "id_secret_quation")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quation")
    @Length(max = 45)
    private String quation;

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

    @OneToMany(mappedBy = "secretQuation", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserAccount> userAccountList;
}

package com.pdev.rempms.adminservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
@Entity
@Data
@Table(name = "user_role")
public class UserRole {
    @Id
    @Column(name = "id_user_role")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_role_name")
    @Length(max = 45)
    private String userRoleName;

    @Column(name = "user_role_description")
    @Length(max = 45)
    private String userRoleDescription;

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

    @OneToMany(mappedBy = "userRole", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserAccount> userAccountList;
}

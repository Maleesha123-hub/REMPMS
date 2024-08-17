package com.pdev.rempms.adminservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;

/**
 * @author maleeshasa
 * @Date 24/12/2023
 */
@Entity
@Data
@Table(name = "user_account")
public class UserAccount {
    @Id
    @Column(name = "id_user_account")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    @Length(max = 45)
    private String userName;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_image")
    private String userImage;

    @Column(name = "last_access_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime lastAccessTime;

    @Column(name = "user_token")
    private String token;

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

    @JoinColumn(name = "secret_quation_id_secret_quation")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SecretQuation secretQuation;

    @JoinColumn(name = "user_role_id_user_role")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserRole userRole;
}

package com.pdev.rempms.recruitmentservice.model.jobPosition;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@Table(name = "job_position")
public class JobPosition {
    @Id
    @Column(name = "id_job_position")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "position")
    @Length(max = 45)
    private String position;

    @Column(name = "industry_id_industry")
    private Integer industryId;

    @Column(name = "active")
    private Boolean active;
}

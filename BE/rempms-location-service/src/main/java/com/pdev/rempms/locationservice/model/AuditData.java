package com.pdev.rempms.locationservice.model;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class AuditData {
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime createdOn;

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime updatedOn;

    public AuditData(LocalDateTime createdOn, String createdBy) {
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    private String createdBy;

    private String updatedBy;
}

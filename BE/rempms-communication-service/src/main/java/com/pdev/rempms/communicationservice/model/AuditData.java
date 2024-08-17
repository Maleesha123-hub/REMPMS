package com.pdev.rempms.communicationservice.model;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;

@Data
@Embeddable
public class AuditData {
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime createdOn;

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime updatedOn;

    private Long createdBy;

    private Long updatedBy;
}

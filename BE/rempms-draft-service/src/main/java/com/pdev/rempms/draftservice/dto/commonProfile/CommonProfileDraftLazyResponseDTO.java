package com.pdev.rempms.draftservice.dto.commonProfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author @Maleesha99
 * @Date 2024/03/01
 */
@Setter
@Getter
@AllArgsConstructor
public class CommonProfileDraftLazyResponseDTO {

    private Long id;

    private LocalDateTime createdDate;

    private String createdBy;

}

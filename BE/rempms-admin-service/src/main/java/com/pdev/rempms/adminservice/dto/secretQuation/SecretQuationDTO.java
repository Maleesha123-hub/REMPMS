package com.pdev.rempms.adminservice.dto.secretQuation;

import com.pdev.rempms.adminservice.model.AuditData;
import lombok.Data;

/**
 * @author maleeshasa
 * @Date 29/12/2023
 */
@Data
public class SecretQuationDTO {
    private String idSecretQuation;
    private String quation;
    private String commonStatus;
    private AuditData auditData;
}

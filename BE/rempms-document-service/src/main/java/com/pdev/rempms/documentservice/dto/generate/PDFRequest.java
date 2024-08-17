package com.pdev.rempms.documentservice.dto.generate;

import lombok.Getter;
import lombok.Setter;

/**
 * @author #Maleesha99
 * @Date 2024/02/24
 */
@Getter
@Setter
public class PDFRequest {

    private String documentName;

    private Object data;

}

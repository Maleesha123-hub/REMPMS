package com.pdev.rempms.candidateservice.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author maleeshasa
 * @Date 23/12/2023
 */
@Configuration
@Data
public class CommonProperties {

    @Value("${document.max.image}")
    private String maxImageSize;

    @Value("${document.max.document}")
    private String maxDocumentSize;
}

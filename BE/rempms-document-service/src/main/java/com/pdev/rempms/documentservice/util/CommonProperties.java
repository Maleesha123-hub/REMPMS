package com.pdev.rempms.documentservice.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author maleeshasa
 * @Date 23/12/2023
 */
@Component
@Getter
@Setter
public class CommonProperties {

    @Value("${document.base.file.path}")
    private String baseFilePath;

    @Value("${document.base.font.path}")
    private String font;

    @Value("${document.max.image}")
    private String maxImageSize;

    @Value("${document.max.document}")
    private String maxDocumentSize;

}

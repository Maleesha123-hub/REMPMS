package com.pdev.rempms.adminservice.util;

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

    @Value("${document.userImage}")
    private String userImageLocation;

    @Value("${document.jobPostImage}")
    private String jobPostImageLocation;

    @Value("${document.cv}")
    private String cvLocation;

    @Value("${document.max.image}")
    private String maxImageSize;

    @Value("${document.max.document}")
    private String maxDocumentSize;
}

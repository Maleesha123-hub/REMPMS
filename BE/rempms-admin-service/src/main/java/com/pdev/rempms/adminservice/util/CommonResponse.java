package com.pdev.rempms.adminservice.util;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author maleeshasa
 * @Date 16/12/2023
 */
@Data
public class CommonResponse {
    private HttpStatus status;
    private String message;
    private Object data;
}

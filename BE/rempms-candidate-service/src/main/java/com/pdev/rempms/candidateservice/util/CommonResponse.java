package com.pdev.rempms.candidateservice.util;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {

    private HttpStatus status;
    private String message;
    private Object data;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
}

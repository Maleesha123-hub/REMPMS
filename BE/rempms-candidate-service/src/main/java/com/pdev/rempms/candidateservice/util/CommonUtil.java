package com.pdev.rempms.candidateservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.rempms.candidateservice.enums.ContentType;
import com.pdev.rempms.candidateservice.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class CommonUtil {

    private final ObjectMapper objectMapper;

    public static String getContentType(String fileNameWithExtension) {
        String extension = FilenameUtils.getExtension(fileNameWithExtension);
        switch (extension) {
            case "pdf" -> {
                return ContentType.PDF.getName();
            }
            case "jpeg", "jpg" -> {
                return ContentType.JPEG.getName();
            }
            case "png" -> {
                return ContentType.PNG.getName();
            }
            default -> throw new BaseException(500, "Invalid file extension.");
        }
    }

    /**
     * This method is allowed to get the current username from security context
     *
     * @return {@link String} - username
     * @author maleeshasa
     */
    public String getUsername() {
        log.info("CommonUtil.getUsername() => started.");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("CommonUtil.getUsername() => ended.");
        return objectMapper.convertValue(principal, String.class);
    }
}

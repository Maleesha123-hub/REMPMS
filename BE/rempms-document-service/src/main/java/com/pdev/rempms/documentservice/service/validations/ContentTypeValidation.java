package com.pdev.rempms.documentservice.service.validations;

import com.pdev.rempms.documentservice.enums.ContentType;
import com.pdev.rempms.documentservice.exception.BaseException;
import org.springframework.stereotype.Component;

@Component
public class ContentTypeValidation {

    public boolean validateContentType(String type) {

        boolean exists = false;

        try {
            for (ContentType contentType : ContentType.values()) {
                if (contentType.equals(ContentType.valueOf(type))) {
                    return true;
                } else {
                    exists = false;
                }
            }

        } catch (Exception e) {
            throw new BaseException(500, "Content type is invalid.");
        }
        return exists;
    }

}

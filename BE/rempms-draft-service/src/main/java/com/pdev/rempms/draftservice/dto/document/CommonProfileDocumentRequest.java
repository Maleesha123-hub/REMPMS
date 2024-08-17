package com.pdev.rempms.draftservice.dto.document;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;

@Getter
@Setter
public class CommonProfileDocumentRequest {

    private Integer documentTypeId;
    private String actualFileName;
    private Binary file;

}

package com.pdev.rempms.recruitmentservice.dto.document;

import com.pdev.rempms.recruitmentservice.constants.enums.FolderType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentDetails {

    private FolderType folderType;
    private String refNo;
    private String documentType;
    private String documentName;

}

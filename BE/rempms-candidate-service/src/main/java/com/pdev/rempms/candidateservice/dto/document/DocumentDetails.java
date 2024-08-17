package com.pdev.rempms.candidateservice.dto.document;

import com.pdev.rempms.candidateservice.constants.enums.FolderType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author @Maleesha99
 * @Date 2024/02/18
 */
@Getter
@Setter
public class DocumentDetails {

    private FolderType folderType;
    private String refNo;
    private String documentType;

}

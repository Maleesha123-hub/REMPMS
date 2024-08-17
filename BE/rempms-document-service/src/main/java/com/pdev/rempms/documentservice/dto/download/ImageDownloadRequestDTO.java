package com.pdev.rempms.documentservice.dto.download;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDownloadRequestDTO {
    private String contentType;
    private String documentPath;
}

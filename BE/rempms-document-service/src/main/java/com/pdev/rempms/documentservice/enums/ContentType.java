package com.pdev.rempms.documentservice.enums;

public enum ContentType {
    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif"),
    BMP("image/bmp"),
    SVG("image/svg+xml"),
    PDF("application/pdf"),
    MICROSOFT_WORD("application/msword"),
    MICROSOFT_EXCEL("application/vnd.ms-excel"),
    MICROSOFT_POWERPOINT("application/vnd.ms-powerpoint"),
    OPEN_DOCUMENT_TEXT("application/vnd.oasis.opendocument.text"),
    OPEN_DOCUMENT_SPREADSHEET("application/vnd.oasis.opendocument.spreadsheet"),
    PLAIN_TEXT("text/plain"),
    HTML("text/html"),
    CSV("text/csv"),
    XML("application/xml"),
    ZIP("application/zip"),
    GZIP("application/gzip"),
    TAR("application/x-tar"),
    MP3("audio/mpeg"),
    MP4("video/mp4"),
    OGG("application/ogg");

    private final String name;

    ContentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

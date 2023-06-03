package com.youngtechcr.www.services.storage;

public enum FileType {

    IMAGE("img"),
    VIDEO("video"),
    PDF("pdf"),
    XLS_OR_XLSX("xlsOrXslx");
    // ^^^ no "_" or "-" are used becasue it can cause confusion and
    // the "-" is a delimiter of server file name structire

    public final String identifier;

    FileType(String identifier) {
        this.identifier = identifier;
    }

}

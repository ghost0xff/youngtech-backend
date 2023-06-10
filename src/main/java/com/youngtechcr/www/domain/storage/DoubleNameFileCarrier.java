package com.youngtechcr.www.domain.storage;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

// Intended to carry Resource with server file name
// and custom file name for client to download with.
// Example: com.youngtechcr.www.domain.storage.FileMetaData implementations wich have a serverFileName and an origianlFileName
public record DoubleNameFileCarrier(Resource resource, String customFileName, MediaType fileMediaType) {

}

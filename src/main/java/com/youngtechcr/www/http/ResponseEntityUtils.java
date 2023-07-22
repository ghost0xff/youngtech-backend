package com.youngtechcr.www.http;

import com.youngtechcr.www.storage.DualNameFileCarrier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;

public final class ResponseEntityUtils {

    private static final Logger log = LoggerFactory.getLogger(ResponseEntityUtils.class);

    public static <T> ResponseEntity<T> created(T createdResource) {
        ResponseEntity<T> responseEntity = new ResponseEntity(createdResource,HttpStatus.CREATED);
        return responseEntity;
    }

    public static <T extends Resource> ResponseEntity<T> downloadedFileWithMetaDataCarrier(DualNameFileCarrier dualNameFileCarrier) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", dualNameFileCarrier.secondFilename());
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + dualNameFileCarrier.secondFilename());
        ResponseEntity responseEntity = ResponseEntity
                .ok()
                .contentType(dualNameFileCarrier.fileMediaType())
                .headers(httpHeaders)
                .body(dualNameFileCarrier.resource());
        return responseEntity;
    }

}

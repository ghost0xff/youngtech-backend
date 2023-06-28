package com.youngtechcr.www.http;

import com.youngtechcr.www.storage.DoubleNameFileCarrier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;

public final class ResponseEntityUtils {

    private static final Logger log = LoggerFactory.getLogger(ResponseEntityUtils.class);

    public static <T> ResponseEntity<T> created(T resourceToBeCreated) {
        ResponseEntity<T> responseEntity = new ResponseEntity(resourceToBeCreated,HttpStatus.CREATED);
        return responseEntity;
    }

    public static <T extends Resource> ResponseEntity<T> downloadedFileWithMetaDataCarrier(DoubleNameFileCarrier doubleNameFileCarrier) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", doubleNameFileCarrier.customFileName());
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + doubleNameFileCarrier.customFileName());
        ResponseEntity responseEntity = ResponseEntity
                .ok()
                .contentType(doubleNameFileCarrier.fileMediaType())
                .headers(httpHeaders)
                .body(doubleNameFileCarrier.resource());
        return responseEntity;
    }

}

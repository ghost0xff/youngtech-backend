package com.youngtechcr.www.http;

import com.youngtechcr.www.storage.DualFilenameBearer;
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

    public static <T extends Resource> ResponseEntity<T> downloadedFileWithMetaDataCarrier(DualFilenameBearer dualFilenameBearer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", dualFilenameBearer.customFileName());
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + dualFilenameBearer.customFileName());
        ResponseEntity responseEntity = ResponseEntity
                .ok()
                .contentType(dualFilenameBearer.fileMediaType())
                .headers(httpHeaders)
                .body(dualFilenameBearer.resource());
        return responseEntity;
    }

}

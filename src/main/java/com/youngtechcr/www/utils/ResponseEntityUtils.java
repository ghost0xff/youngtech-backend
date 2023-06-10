package com.youngtechcr.www.utils;

import com.youngtechcr.www.exceptions.custom.FileOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;

import java.io.IOException;
import java.nio.file.Files;

public final class ResponseEntityUtils {

    private static final Logger log = LoggerFactory.getLogger(ResponseEntityUtils.class);

    public static <T> ResponseEntity<T> created(T resourceToBeCreated) {
        ResponseEntity<T> responseEntity = new ResponseEntity(resourceToBeCreated,HttpStatus.CREATED);
        return responseEntity;
    }

    public static <T extends Resource> ResponseEntity<T> downloadedFileWithCustomName(T resourceToBeDownloaded, String customFileName, MediaType fileMediaType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", customFileName);
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + customFileName);
        ResponseEntity<T> responseEntity = ResponseEntity.ok().contentType(fileMediaType).headers(httpHeaders).body(resourceToBeDownloaded);
        return responseEntity;
    }

}

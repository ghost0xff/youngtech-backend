package com.youngtechcr.www.exceptions.custom;

import com.youngtechcr.www.exceptions.AbstractRuntimeException;
import com.youngtechcr.www.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;

public class FileOperationException extends AbstractRuntimeException  {

    public FileOperationException(
                String detail
    ) {
            super(
                    "Can't process file",
                    ErrorCode.from(5),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    detail
            );
    }
}

package com.youngtechcr.www.exceptions;

public final class ExceptionUtils {

    public static <T extends RuntimeException & CustomRuntimeException> CustomProblemDetail handle(T exception) {
        CustomProblemDetail customProblemDetail = CustomProblemDetail.of(
                exception.getDefaultTitle(),
                exception.getDetail(),
                exception.getDefaultHttpStatus(),
                exception.getCustomErrorCode()
        );
        return customProblemDetail;
    }

}

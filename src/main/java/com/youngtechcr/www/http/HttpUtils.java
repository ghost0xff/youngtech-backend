package com.youngtechcr.www.http;

import org.springframework.http.HttpMethod;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public final class HttpUtils {

    public static List<String> getAllHttpMethods() {
        return Arrays.asList(
                HttpMethod.GET.toString(),
                HttpMethod.POST.toString(),
                HttpMethod.PUT.toString(),
                HttpMethod.DELETE.toString(),
                HttpMethod.OPTIONS.toString(),
                HttpMethod.HEAD.toString(),
                HttpMethod.TRACE.toString(),
                HttpMethod.PATCH.toString()
        );
    }

}

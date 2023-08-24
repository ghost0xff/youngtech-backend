package com.youngtechcr.www.http;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class HttpUtils {

    public static List<String> getCommonHttpMethods() {
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

    public static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            if (values.length > 0) {
                for (String value : values) {
                    parameters.add(key, value);
                }
            }
        });
        return parameters;
    }
}

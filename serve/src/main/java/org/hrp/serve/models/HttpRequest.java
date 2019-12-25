package org.hrp.serve.models;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class HttpRequest {

    String path;
    String method;
    String body;
    Map<String, Object> headers;
    Map<String, Cookie> cookies;
    Map<String, Object> queryParameters;

    public static HttpRequest fromServletRequest(HttpServletRequest request) throws IOException {
        HttpRequest req = new HttpRequest();
        req.path = request.getRequestURI().substring(request.getContextPath().length());
        req.method = request.getMethod();
        req.body = IOUtils.toString(request.getReader());
        req.headers = parseHeaders(request);
        req.cookies = parseCookies(request);
        req.queryParameters = parseQueryParameters(request);
        return req;
    }


    static Map<String, Object> parseHeaders(HttpServletRequest request) {
        return Collections
                .list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(h -> h, request::getHeader));
    }

    static Map<String, Cookie> parseCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .collect(Collectors.toMap(Cookie::getName, c -> c));
        }

        return ImmutableMap.of();
    }

    static Map<String, Object> parseQueryParameters(HttpServletRequest request) {
        if (request.getParameterNames() != null) {
            return Collections.list(request.getParameterNames())
                    .stream()
                    .collect(Collectors.toMap(parameterName -> parameterName, parameterName -> {
                        String[] stringValues = request.getParameterValues(parameterName);

                        List<Serializable> objectValue = Arrays.stream(stringValues).map(v -> {
                            if (NumberUtils.isParsable(v)) {
                                try {
                                    return NumberFormat.getInstance().parse(v);
                                } catch (ParseException e) {
                                    log.warn("can not parse {} to number", v, e);
                                }
                            }
                            return v;
                        }).collect(Collectors.toList());

                        if (objectValue.size() == 1) {
                            return objectValue.get(0);
                        }
                        return objectValue;
                    }));
        }
        return ImmutableMap.of();
    }
}

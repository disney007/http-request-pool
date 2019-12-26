package org.hrp.serve;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.hrp.serve.models.HttpRequest;

public class Utils {
    public static HttpRequest createDefaultHttpRequest() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setPath("/test");
        httpRequest.setMethod("post");
        httpRequest.setQueryParameters(ImmutableMap.of("x", "hello", "y", 343, "z", ImmutableList.of("world", "man")));
        httpRequest.setBody("{\"a\":123, \"b\":\"efg\"}");
        httpRequest.setHeaders(ImmutableMap.of("TOKEN", "abcdefg12345"));
        return httpRequest;
    }
}

package org.hrp.serve.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hrp.lib.models.HttpRequest;
import org.springframework.stereotype.Service;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConditionService {

    static String PATH = "$path";
    static String METHOD = "$method";
    static String HEADERS = "$headers";
    static String QUERY_PARAMETERS = "$queryParams";
    static String BODY_STRING = "$bodyString";
    static String BODY_JSON = "$body";
    static String COOKIES = "$cookies";

    final NashornScriptEngine scriptEngine;
    final ObjectMapper objectMapper;

    ConcurrentHashMap<String, CompiledScript> scripts = new ConcurrentHashMap<>();

    public boolean testRequest(HttpRequest request, String condition) {
        try {
            log.info("test condition for path = [{}], condition = [{}]", request.getPath(), condition);
            CompiledScript script = prepareScript(condition);
            Bindings bindings = prepareBindings(request);
            boolean result = parseResult(script.eval(bindings));
            log.info("test condition for path = [{}], result = [{}]", request.getPath(), result);
            return result;
        } catch (ScriptException e) {
            log.warn("script error occurred during script execution for request {}, script = {}", request, condition, e);
        } catch (Exception e) {
            log.error("general error occurred during script execution for request {}, script = {}", request, condition, e);
        }
        return false;
    }

    boolean parseResult(Object result) {
        if (result == null) {
            return false;
        }

        if (result instanceof Boolean) {
            return (Boolean) result;
        }

        return BooleanUtils.toBoolean(result.toString());
    }

    CompiledScript prepareScript(String condition) throws ScriptException {
        if (scripts.containsKey(condition)) {
            return scripts.get(condition);
        }

        CompiledScript script = scriptEngine.compile(condition);
        scripts.put(condition, script);
        return script;
    }

    Bindings prepareBindings(HttpRequest request) {
        Bindings bindings = scriptEngine.createBindings();
        bindings.put(PATH, request.getPath());
        bindings.put(METHOD, request.getMethod());
        bindings.put(HEADERS, request.getHeaders());
        bindings.put(QUERY_PARAMETERS, request.getQueryParameters());
        bindings.put(BODY_STRING, request.getBody());
        bindings.put(BODY_JSON, getBodyJson(request));
        bindings.put(COOKIES, request.getCookies());

        return bindings;
    }

    Map getBodyJson(HttpRequest request) {
        String body = request.getBody();
        try {
            if (StringUtils.isNotEmpty(body)) {
                return objectMapper.readValue(request.getBody(), Map.class);
            }

        } catch (IOException e) {
            // the body is not json
        }
        return ImmutableMap.of();
    }
}

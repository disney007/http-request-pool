package org.hrp.serve.services;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.hrp.serve.models.HttpRequest;
import org.springframework.stereotype.Service;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ConditionService {

    static String PATH = "$path";
    static String METHOD = "$method";
    static String HEADERS = "$headers";
    static String QUERY_PARAMETERS = "$queryParams";
    static String BODY_STRING = "$bodyString";
    static String BODY_JSON = "$body";
    static String COOKIES = "$cookies";

    final NashornScriptEngine scriptEngine;

    ConcurrentHashMap<String, CompiledScript> scripts = new ConcurrentHashMap<>();

    public boolean testRequest(HttpRequest request, String condition) throws ScriptException {
        CompiledScript script = prepareScript(condition);
        Bindings bindings = prepareBindings(request);
        return parseResult(script.eval(bindings));
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
        bindings.put(BODY_JSON, request.getBody()); // todo later
        bindings.put(COOKIES, request.getCookies());

        return bindings;
    }
}

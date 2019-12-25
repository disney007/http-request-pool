package org.hrp.serve;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrp.serve.models.HttpRequest;
import org.hrp.serve.services.ConditionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RequestHandler {
    final ConditionService conditionService;

    @RequestMapping
    public Object handle(HttpServletRequest servletRequest) throws ScriptException, IOException {

        HttpRequest request = HttpRequest.fromServletRequest(servletRequest);
        return conditionService.testRequest(request, request.getBody());
    }
}

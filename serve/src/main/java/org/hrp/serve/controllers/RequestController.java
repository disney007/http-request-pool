package org.hrp.serve.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrp.serve.models.HttpRequest;
import org.hrp.serve.models.HttpResponse;
import org.hrp.serve.services.RequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    final RequestService requestService;

    @RequestMapping
    public Object handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ScriptException, IOException {
        HttpRequest request = HttpRequest.fromServletRequest(servletRequest);
        HttpResponse response = requestService.processRequest(request);
        servletResponse.setStatus(response.getStatus());
        return response.getBody();
    }
}

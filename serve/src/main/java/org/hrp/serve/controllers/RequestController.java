package org.hrp.serve.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hrp.lib.models.HttpRequest;
import org.hrp.lib.models.HttpResponse;
import org.hrp.lib.services.RequestHistoryService;
import org.hrp.serve.services.RequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    final RequestService requestService;
    final RequestHistoryService requestHistoryService;

    @RequestMapping
    public Object handle(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {
        HttpRequest request = HttpRequest.fromServletRequest(servletRequest);
        HttpResponse response = requestService.processRequest(request);

        requestHistoryService.createRequestEntity(request, response);
        servletResponse.setStatus(response.getStatus());
        return response.getBody();
    }
}

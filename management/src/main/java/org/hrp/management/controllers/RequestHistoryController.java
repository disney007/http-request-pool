package org.hrp.management.controllers;

import lombok.RequiredArgsConstructor;
import org.hrp.lib.entities.RequestHistoryEntity;
import org.hrp.lib.models.Resource;
import org.hrp.lib.repositories.RequestHistoryRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/requestHistory")
@RequiredArgsConstructor
public class RequestHistoryController {

    final RequestHistoryRepository requestHistoryRepository;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Resource<RequestHistoryEntity> queryRequestHistory(
            @PathParam("path") @Valid @NotNull String path,
            @PathParam("method") String method,
            @PathParam("pageIndex") @Valid @NotNull Integer pageIndex,
            @PathParam("pageSize") @Valid @NotNull Integer pageSize
    ) {
        return requestHistoryRepository.query(path, method, pageIndex, pageSize);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RequestHistoryEntity findById(@PathVariable("id") @Valid @NotNull String id) {
        return requestHistoryRepository.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("id") @Valid @NotNull String id) {
        requestHistoryRepository.deleteById(id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteByPathAndMethod(
            @PathParam("path") @Valid @NotNull String path,
            @PathParam("method") String method
    ) {
        requestHistoryRepository.deleteByPathAndMethod(path, method);
    }
}

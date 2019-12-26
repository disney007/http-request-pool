package org.hrp.lib.services;

import lombok.RequiredArgsConstructor;
import org.hrp.lib.entities.RequestHistoryEntity;
import org.hrp.lib.models.HttpRequest;
import org.hrp.lib.models.HttpResponse;
import org.hrp.lib.repositories.RequestHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestHistoryService {
    final RequestHistoryRepository requestHistoryRepository;

    public RequestHistoryEntity createRequestEntity(HttpRequest request, HttpResponse response) {
        RequestHistoryEntity entity = new RequestHistoryEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setMethod(request.getMethod());
        entity.setPath(request.getPath());
        entity.setCreatedAt(System.currentTimeMillis());
        entity.setRequest(request);
        entity.setResponse(response);

        requestHistoryRepository.save(entity);
        return entity;
    }
}

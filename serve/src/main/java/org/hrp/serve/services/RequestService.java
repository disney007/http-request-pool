package org.hrp.serve.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hrp.lib.entities.RuleEntity;
import org.hrp.lib.models.HttpRequest;
import org.hrp.lib.models.HttpResponse;
import org.hrp.lib.repositories.RuleRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {

    final ConditionService conditionService;
    final RuleRepository ruleRepository;
    final ObjectMapper objectMapper;

    public HttpResponse processRequest(HttpRequest request) {
        log.info("start processing request: {}", request);
        HttpResponse response = new HttpResponse();

        List<RuleEntity> rules = ruleRepository.findByPath(request.getPath());
        log.info("found {} rules for path [{}]", rules.size(), request.getPath());
        if (rules.isEmpty()) {
            return response;
        }

        rules.stream().filter(rule -> rule.getIsEnabled()
                && (StringUtils.equalsIgnoreCase(rule.getMethod(), request.getMethod()) || StringUtils.equals(rule.getMethod(), "*"))
                && conditionService.testRequest(request, rule.getCondition())
        ).findFirst().ifPresent(ruleEntity -> {
            log.info("found rule [{}] for path [{}]", ruleEntity, request.getPath());
            response.setStatus(ruleEntity.getReturnHttpCode());
            response.setBody(formatResponseBody(ruleEntity.getReturnJson()));
        });

        log.info("processing result for path [{}] , status = [{}], body = [{}]", request.getPath(), response.getStatus(), response.getBody());
        return response;
    }

    Object formatResponseBody(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            return json;
        }
    }
}

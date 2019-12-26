package org.hrp.lib.services;

import lombok.RequiredArgsConstructor;
import org.hrp.lib.entities.RuleEntity;
import org.hrp.lib.repositories.RuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RuleService {

    final RuleRepository ruleRepository;

    public RuleEntity createRule(String path, String method, String condition, Integer returnHttpCode, String returnJson) {
        RuleEntity ruleEntity = RuleEntity.builder()
                .id(UUID.randomUUID().toString())
                .path(path)
                .method(method)
                .condition(condition)
                .returnHttpCode(returnHttpCode)
                .returnJson(returnJson)
                .isEnabled(true)
                .createdAt(System.currentTimeMillis())
                .build();
        ruleRepository.save(ruleEntity);

        return ruleEntity;
    }

    public void editRule(String id, String path, String method, String condition, Integer returnHttpCode, String returnJson,
                         Boolean isEnabled) {
        RuleEntity ruleEntity = ruleRepository.findById(id);
        if (ruleEntity != null) {
            ruleEntity.setPath(path);
            ruleEntity.setMethod(method);
            ruleEntity.setCondition(condition);
            ruleEntity.setReturnHttpCode(returnHttpCode);
            ruleEntity.setReturnJson(returnJson);
            ruleEntity.setIsEnabled(isEnabled);
            ruleRepository.save(ruleEntity);
        }
    }

    public List<RuleEntity> findAllRules() {
        return ruleRepository.findAll();
    }

    public List<RuleEntity> findByPath(String path) {
        return ruleRepository.findByPath(path);
    }
}

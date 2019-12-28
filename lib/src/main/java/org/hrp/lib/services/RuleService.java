package org.hrp.lib.services;

import lombok.RequiredArgsConstructor;
import org.hrp.lib.entities.RuleEntity;
import org.hrp.lib.models.RuleRecord;
import org.hrp.lib.repositories.RuleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RuleService {

    final RuleRepository ruleRepository;

    public RuleEntity createRule(RuleRecord ruleRecord) {
        RuleEntity ruleEntity = RuleEntity.builder()
                .id(ruleRecord.getId())
                .path(ruleRecord.getPath())
                .method(ruleRecord.getMethod())
                .condition(ruleRecord.getCondition())
                .returnHttpCode(ruleRecord.getReturnHttpCode())
                .returnJson(ruleRecord.getReturnJson())
                .isEnabled(ruleRecord.getIsEnabled())
                .createdAt(System.currentTimeMillis())
                .build();
        ruleRepository.save(ruleEntity);

        return ruleEntity;
    }

    public void editRule(RuleRecord ruleRecord) {
        RuleEntity ruleEntity = ruleRepository.findById(ruleRecord.getId());
        if (ruleEntity != null) {
            ruleEntity.setPath(ruleRecord.getPath());
            ruleEntity.setMethod(ruleRecord.getMethod());
            ruleEntity.setCondition(ruleRecord.getCondition());
            ruleEntity.setReturnHttpCode(ruleRecord.getReturnHttpCode());
            ruleEntity.setReturnJson(ruleRecord.getReturnJson());
            ruleEntity.setIsEnabled(ruleRecord.getIsEnabled());
            ruleRepository.save(ruleEntity);
        }
    }
}

package org.hrp.lib.repositories;

import lombok.RequiredArgsConstructor;
import org.hrp.lib.entities.RuleEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RuleRepository {

    final MongoTemplate mongoTemplate;

    public void save(RuleEntity ruleEntity) {
        mongoTemplate.save(ruleEntity);
    }

    public List<RuleEntity> findAll() {
        return mongoTemplate.findAll(RuleEntity.class);
    }

    public RuleEntity findById(String id) {
        return mongoTemplate.findById(id, RuleEntity.class);
    }
}

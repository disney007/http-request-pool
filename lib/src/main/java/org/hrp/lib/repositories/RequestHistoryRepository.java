package org.hrp.lib.repositories;

import lombok.RequiredArgsConstructor;
import org.hrp.lib.entities.RequestHistoryEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RequestHistoryRepository {
    final MongoTemplate mongoTemplate;

    public void save(RequestHistoryEntity requestHistoryEntity) {
        mongoTemplate.save(requestHistoryEntity);
    }
}

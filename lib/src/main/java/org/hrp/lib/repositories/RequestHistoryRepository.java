package org.hrp.lib.repositories;

import com.mongodb.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.hrp.lib.entities.RequestHistoryEntity;
import org.hrp.lib.models.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class RequestHistoryRepository {
    final MongoTemplate mongoTemplate;

    public void save(RequestHistoryEntity requestHistoryEntity) {
        mongoTemplate.save(requestHistoryEntity);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), RequestHistoryEntity.class);
    }

    public void deleteByPathAndMethod(@NotNull String path, @Nullable String method) {
        Criteria criteria = Criteria.where("path").is(path);
        if (method != null) {
            criteria = criteria.and("method").is(method);
        }
        mongoTemplate.remove(Query.query(criteria), RequestHistoryEntity.class);
    }

    public RequestHistoryEntity findById(String id) {
        return mongoTemplate.findById(id, RequestHistoryEntity.class);
    }

    public Resource<RequestHistoryEntity> query(@NotNull String path, @Nullable String method, int pageIndex, int pageSize) {
        Criteria criteria = Criteria.where("path").is(path);

        if (method != null) {
            criteria = criteria.and("method").is(method);
        }
        Query query = Query.query(criteria);
        long totalCount = mongoTemplate.count(query, RequestHistoryEntity.class);

        query.with(Sort.by(Sort.Order.desc("createdAt")));
        query.with(PageRequest.of(pageIndex, pageSize));
        List<RequestHistoryEntity> entities = mongoTemplate.find(query, RequestHistoryEntity.class);
        return new Resource<>(entities, totalCount, pageIndex, pageSize);
    }
}

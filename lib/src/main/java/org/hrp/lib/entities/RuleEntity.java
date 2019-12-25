package org.hrp.lib.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rule")
@Data
@Builder
public class RuleEntity {
    @Id
    String id;

    @Indexed
    String path;
    String method;
    String condition;

    Integer returnHttpCode;
    String returnJson;

    Boolean isEnabled;
    Long createdAt;
}

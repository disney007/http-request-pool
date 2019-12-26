package org.hrp.lib.entities;

import lombok.Data;
import org.hrp.lib.models.HttpRequest;
import org.hrp.lib.models.HttpResponse;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "requestHistory")
@Data
public class RequestHistoryEntity {
    @Id
    String id;
    @Indexed
    String path;
    String method;
    HttpRequest request;
    HttpResponse response;
    Long createdAt;
}

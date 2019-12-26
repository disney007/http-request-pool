package org.hrp.lib.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpResponse {
    Object body;
    int status = 200;
}

package org.hrp.lib.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleRecord {
    String id;
    String path;
    String method;
    String condition;
    Integer returnHttpCode;
    String returnJson;
    Boolean isEnabled;

    public void setMethod(String method) {
        this.method = StringUtils.upperCase(method);
    }
}

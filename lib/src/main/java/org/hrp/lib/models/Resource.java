package org.hrp.lib.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Resource<T> {
    List<T> items;
    long totalCount;
    int pageIndex;
    int pageSize;
}

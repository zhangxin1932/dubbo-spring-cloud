package com.zy.producer.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class PageResult<T> {
    private int page;
    private int size;
    private int totalSize;
    private int totalPages;
    private List<T> result;
}

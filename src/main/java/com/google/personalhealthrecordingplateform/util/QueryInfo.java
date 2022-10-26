package com.google.personalhealthrecordingplateform.util;

import lombok.Data;

@Data
public class QueryInfo {
    /**
     * 第几页
     */
    private Integer pageNumber;
    /**
     * 一页包含多少条数据
     */
    private Integer pageSize;

    /**
     * 模糊查询字符串
     */
    private String queryString;
}

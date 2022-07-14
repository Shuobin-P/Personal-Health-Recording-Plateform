package com.google.personalhealthrecordingplateform.utils;

import lombok.Data;

@Data
public class QueryInfo {
    private Integer pageNumber;
    private Integer pageSize;
    private String queryString;
}

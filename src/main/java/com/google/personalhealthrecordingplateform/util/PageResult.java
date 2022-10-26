package com.google.personalhealthrecordingplateform.util;

import lombok.Data;

import java.util.List;

@Data
//@EqualsAndHashCode(callSuper = true)
public class PageResult extends Result {
    /**
     * 总数据量
     */
    private long total;
    /**
     * 一页的数据
     */
    private List<?> rows;

    public PageResult(long total, List<?> rows) {
        this.setFlag(true);
        this.setMessage("分页查询成功");
        this.total = total;
        this.rows = rows;
    }
}

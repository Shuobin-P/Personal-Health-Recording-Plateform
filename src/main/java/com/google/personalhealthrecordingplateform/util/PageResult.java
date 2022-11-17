package com.google.personalhealthrecordingplateform.util;

import lombok.Data;

import java.util.List;

/**
 * @author 31204
 */
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
    /**
     * 直接返回分页数据
     * @param total 分页总条数
     * @param list 分页数据列表
     * @return
     */
    public static Result pageResult(long total, List<?> list) {
        return new PageResult(total, list);
    }
}

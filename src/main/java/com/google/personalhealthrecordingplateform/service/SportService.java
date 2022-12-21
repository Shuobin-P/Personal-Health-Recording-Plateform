package com.google.personalhealthrecordingplateform.service;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.Sport;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/21 21:12
 */
public interface SportService {
    /**
     * 插入运动项目
     *
     * @param sport
     */
    void insert(Sport sport);

    /**
     * 删除运动项目
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 更新运动项目
     *
     * @param sport
     */
    void update(Sport sport);

    /**
     * 分页查询
     *
     * @param queryString
     * @return
     */
    Page<Sport> findPage(String queryString);
}

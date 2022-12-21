package com.google.personalhealthrecordingplateform.service;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.SportNews;


/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/16 12:29
 */
public interface SportNewsService {
    /**
     * 插入运动项目
     *
     * @param sportNews
     */
    void insert(SportNews sportNews);

    /**
     * 删除运动项目
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 更新运动项目信息
     *
     * @param sportNews
     */
    void update(SportNews sportNews);

    /**
     * 查询运动项目
     *
     * @param queryString
     * @return
     */
    Page<SportNews> findPage(String queryString);
}

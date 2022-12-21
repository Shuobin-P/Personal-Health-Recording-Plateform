package com.google.personalhealthrecordingplateform.mapper;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.Sport;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/21 21:41
 */
@Mapper
public interface SportMapper {
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
     * 修改运动项目
     *
     * @param sportNews
     */
    void update(Sport sportNews);

    /**
     * 分页查询
     *
     * @param queryString
     * @return
     */
    Page<Sport> findPage(String queryString);
}

package com.google.personalhealthrecordingplateform.mapper;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.SportNews;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/16 21:37
 */
@Mapper
public interface SportNewsMapper {
    /**
     * 插入运动资讯
     *
     * @param sportNews
     */
    void insert(SportNews sportNews);

    /**
     * 删除运动资讯
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改运动资讯
     *
     * @param sportNews
     */
    void update(SportNews sportNews);

    /**
     * 分页查询
     *
     * @param queryString
     * @return
     */
    Page<SportNews> findPage(String queryString);
}

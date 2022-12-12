package com.google.personalhealthrecordingplateform.mapper;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.Food;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/8 16:16
 */
@Mapper
public interface FoodMapper {
    /**
     * 插入食物信息
     *
     * @param food
     */
    void insert(Food food);

    /**
     * 批量插入数据
     *
     * @param list 食品数据列表
     */
    void batchInsert(List<Food> list);

    /**
     * 删除食物信息
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改食物信息
     *
     * @param food 修改后得食物信息
     */
    void update(Food food);

    /**
     * @param queryString
     * @return
     */
    Page<Food> findFoodPage(String queryString);
}

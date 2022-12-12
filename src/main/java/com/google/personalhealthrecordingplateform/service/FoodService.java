package com.google.personalhealthrecordingplateform.service;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.Food;
import com.google.personalhealthrecordingplateform.entity.FoodType;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/7 15:53
 */

public interface FoodService {
    /**
     * @param food
     */
    void insert(Food food);

    /**
     * 批量插入数据
     *
     * @param list
     */
    void batchInsert(List<Food> list);

    void delete(Long id);

    void update(Food food);

    /**
     * 查询到所有类型的食物
     *
     * @return
     */
    List<FoodType> getAllFoodType();

    /**
     * 对食物类型进行分页查询
     *
     * @param queryString 查询条件
     * @return 食物类型列表
     */
    Page<FoodType> findFoodTypePage(String queryString);

    /**
     * @param queryString
     * @return
     */
    Page<Food> findFoodPage(String queryString);

    /**
     * 通过食物分类名找到对应的ID
     *
     * @param type 食物分类
     * @return 食物分类对应的ID
     */
    Long findTypeIDByTypeTitle(String type);

}

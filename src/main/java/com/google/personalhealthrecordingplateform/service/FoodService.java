package com.google.personalhealthrecordingplateform.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.Food;
import com.google.personalhealthrecordingplateform.entity.FoodType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    void delete(Long id);

    void update(Food food);

    Food findFoodInfoById(Long id);

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

    Page<Food> findMiniFoodPage(Long typeId, String queryString);

    void insertFoodType(FoodType foodType);

    void deleteFoodType(Long id);

    void updateFoodType(FoodType foodType);


    /**
     * 通过食物分类名找到对应的ID
     *
     * @param type 食物分类
     * @return 食物分类对应的ID
     */
    Long findTypeIDByTypeTitle(String type);

    /**
     * 批量导入食品信息
     *
     * @param file
     */
    void importFoodExcel(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException;

}

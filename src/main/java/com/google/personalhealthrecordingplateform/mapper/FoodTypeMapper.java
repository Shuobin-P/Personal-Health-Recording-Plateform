package com.google.personalhealthrecordingplateform.mapper;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.FoodType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/7 16:01
 */

@Mapper
public interface FoodTypeMapper {
    /**
     * 添加食物分类
     *
     * @param foodType
     */
    void insert(FoodType foodType);

    /**
     * 删除食物分类
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改食物分类
     *
     * @param foodType
     */
    void update(FoodType foodType);


    List<FoodType> getAllFoodType();

    Page<FoodType> findFoodTypePage(String queryString);

    Long findTypeIDByTypeTitle(String type);
}

package com.google.personalhealthrecordingplateform.service.impl;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.Food;
import com.google.personalhealthrecordingplateform.entity.FoodType;
import com.google.personalhealthrecordingplateform.mapper.FoodMapper;
import com.google.personalhealthrecordingplateform.mapper.FoodTypeMapper;
import com.google.personalhealthrecordingplateform.service.FoodService;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/7 16:01
 */
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodTypeMapper foodTypeMapper;
    private final FoodMapper foodMapper;

    @Autowired
    public FoodServiceImpl(FoodTypeMapper foodTypeMapper, FoodMapper foodMapper) {
        this.foodTypeMapper = foodTypeMapper;
        this.foodMapper = foodMapper;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void insert(Food food) {
        foodMapper.insert(food);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void batchInsert(List<Food> list) {
        foodMapper.batchInsert(list);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void delete(Long id) {
        foodMapper.delete(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(Food food) {
        foodMapper.update(food);
    }

    @Override
    public List<FoodType> getAllFoodType() {
        return foodTypeMapper.getAllFoodType();
    }

    @Override
    public Page<FoodType> findFoodTypePage(String queryString) {
        return foodTypeMapper.findFoodTypePage(queryString);
    }

    @Override
    public Page<Food> findFoodPage(String queryString) {
        return foodMapper.findFoodPage(queryString);
    }

    @Override
    public Long findTypeIDByTypeTitle(String type) {
        return foodTypeMapper.findTypeIDByTypeTitle(type);
    }


}

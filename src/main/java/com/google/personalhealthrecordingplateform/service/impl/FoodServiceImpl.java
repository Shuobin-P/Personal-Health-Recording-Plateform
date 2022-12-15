package com.google.personalhealthrecordingplateform.service.impl;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.dto.FoodExcelDTO;
import com.google.personalhealthrecordingplateform.entity.Food;
import com.google.personalhealthrecordingplateform.entity.FoodType;
import com.google.personalhealthrecordingplateform.enumeration.ExcelTypeEnum;
import com.google.personalhealthrecordingplateform.mapper.FoodMapper;
import com.google.personalhealthrecordingplateform.mapper.FoodTypeMapper;
import com.google.personalhealthrecordingplateform.service.FoodService;
import com.google.personalhealthrecordingplateform.service.PoiExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/7 16:01
 */
@Slf4j
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodMapper foodMapper;
    private final FoodTypeMapper foodTypeMapper;
    private final PoiExcelService poiExcelService;

    @Autowired
    public FoodServiceImpl(FoodTypeMapper foodTypeMapper, FoodMapper foodMapper, PoiExcelService poiExcelService) {
        this.foodTypeMapper = foodTypeMapper;
        this.foodMapper = foodMapper;
        this.poiExcelService = poiExcelService;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void insert(Food food) {
        foodMapper.insert(food);
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

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void insertFoodType(FoodType foodType) {
        foodTypeMapper.insert(foodType);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteFoodType(Long id) {
        foodTypeMapper.delete(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updateFoodType(FoodType foodType) {
        foodTypeMapper.update(foodType);
    }

    @Override
    public Long findTypeIDByTypeTitle(String type) {
        return foodTypeMapper.findTypeIDByTypeTitle(type);
    }

    @Override
    public void importFoodExcel(MultipartFile file) throws IOException, IllegalAccessException, InstantiationException {
        try {
            InputStream inputStream = file.getInputStream();
            ExcelTypeEnum excelTypeEnum = file.getOriginalFilename().endsWith(".xls") ? ExcelTypeEnum.XLS : ExcelTypeEnum.XLSX;
            List<FoodExcelDTO> foodExcelDTOS = poiExcelService.read(inputStream, FoodExcelDTO.class, excelTypeEnum);

            // ---- 转化数据 -----
            List<Food> foods = new ArrayList<>();
            log.info("FoodExcelDTOS的大小为：" + foodExcelDTOS.size());
            for (FoodExcelDTO foodExcelDTO : foodExcelDTOS) {
                Food food = new Food();
                BeanUtils.copyProperties(foodExcelDTO, food);
                // ——好好学习人家是怎么用反射的！！！ 学会了可以写在简历的<个人收获>
                food.setTypeId(foodTypeMapper.findTypeIDByTypeTitle(foodExcelDTO.getTypeTitle()));
                foods.add(food);
            }

            // ----- 持久化数据 -----
            foodMapper.batchInsert(foods);
        } catch (IllegalAccessException | InstantiationException | IOException e) {
            throw e;
        } finally {
            try {
                file.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}

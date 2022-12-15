package com.google.personalhealthrecordingplateform.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.personalhealthrecordingplateform.entity.Food;
import com.google.personalhealthrecordingplateform.entity.FoodType;
import com.google.personalhealthrecordingplateform.exception.FoodTypeException;
import com.google.personalhealthrecordingplateform.service.FoodService;
import com.google.personalhealthrecordingplateform.util.PageResult;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/7 15:34
 */
@RequestMapping("/food")
@RestController
@Slf4j
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @ApiOperation(value = "添加菜品")
    @PostMapping("/insert")
    public Result insertFood(@RequestBody Food food) {
        try {
            foodService.insert(food);
        } catch (Exception e) {
            return Result.fail("服务器内部错误，插入食物信息失败");
        }
        return Result.fail("成功插入食物信息");
    }

    @ApiOperation(value = "批量导入菜品")
    @PostMapping("/batchImport")
    public Result importBatchOfFood(@RequestParam("file") MultipartFile multipartFile) {
        //TODO 导入excel文件,其中包含图片，并存入数据库。
        //POI 若一个excel中包含有图片和文字，图片和文字要分开处理。
        //TODO 参考博客：https://blog.csdn.net/wangzhihao1994/article/details/101177290
        try {
            foodService.importFoodExcel(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("服务器内部错误，插入数据失败");
        }
        return Result.success("已成功批量导入数据");
    }

    @ApiOperation(value = "删除菜品")
    @DeleteMapping("/delete/{id}")
    public Result deleteFood(@PathVariable Long id) {
        try {
            foodService.delete(id);
        } catch (Exception e) {
            return Result.fail("服务器内部错误，插入食物信息失败");
        }
        return Result.fail("成功删除食物信息");
    }

    @ApiOperation(value = "修改食物数据接口")
    @PostMapping("/update")
    public Result updateFood(@RequestBody Food food) {
        try {
            log.info("进入更改食物信息接口");
            foodService.update(food);
        } catch (Exception e) {
            return Result.fail("服务器内部错误，更新失败");
        }
        return Result.success("成功修改食品信息");
    }

    @ApiOperation(value = "分页查询食品")
    @PostMapping("/findPage")
    public Result findFoodPage(@RequestBody QueryInfo queryInfo) {
        //对食物进行分页查询
        log.info("进入食物分页查询");
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Food> page = foodService.findFoodPage(queryInfo.getQueryString());
        return PageResult.pageResult(page.getTotal(), page.getResult());
    }

    @ApiOperation(value = "添加菜品分类")
    @PostMapping("/type/insert")
    public Result insertType(@RequestBody FoodType foodType) throws FoodTypeException {
        try {
            foodService.insertFoodType(foodType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FoodTypeException("服务器内部错误，添加菜品分类失败");
        }
        return Result.success("成功添加食物分类");
    }

    @ApiOperation(value = "删除菜品分类")
    @DeleteMapping("/type/delete/{id}")
    public Result deleteType(@PathVariable Long id) throws FoodTypeException {
        try {
            foodService.deleteFoodType(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FoodTypeException("服务器内部错误，删除菜品分类失败");
        }
        return Result.success("成功删除菜品分类");
    }

    @ApiOperation(value = "修改菜品分类")
    @PostMapping("/type/update")
    public Result updateType(@RequestBody FoodType foodType) throws FoodTypeException {
        try {
            foodService.updateFoodType(foodType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FoodTypeException("服务器内部错误，修改菜品分类失败");
        }
        return Result.success("成功修改菜品分类");
    }

    @ApiOperation(value = "分页查询食品分类")
    @PostMapping("/type/findPage")
    public Result findFoodTypePage(@RequestBody QueryInfo queryInfo) {
        //对食物类别进行分页查询
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<FoodType> page = foodService.findFoodTypePage(queryInfo.getQueryString());
        return PageResult.pageResult(page.getTotal(), page.getResult());
    }

    @GetMapping("/typeAll")
    public Result getAllType() {
        return Result.success("分类查询成功", foodService.getAllFoodType());
    }
}

package com.google.personalhealthrecordingplateform.dto;

import com.google.personalhealthrecordingplateform.annotation.ExcelImageData;
import com.google.personalhealthrecordingplateform.annotation.ExcelProperty;
import lombok.Data;

/**
 * 面向表现层 参考https://www.cnblogs.com/cxy2020/p/12332505.html
 *
 * @author W&F
 * @version 1.0
 * @date 2022/12/13 12:38
 */

@Data
public class FoodExcelDTO {
    @ExcelProperty(value = "食物类别", index = 1)
    private String typeTitle;

    @ExcelProperty(value = "食物名称", index = 2)
    private String title;

    @ExcelImageData
    @ExcelProperty(value = "食物图片", index = 3)
    private String imageUrls;

    @ExcelProperty(value = "营养元素", index = 4)
    private String nutrient;

    @ExcelProperty(value = "热量", index = 5)
    private Float heat;

    @ExcelProperty(value = "蛋白质", index = 6)
    private Float protein;

    @ExcelProperty(value = "脂肪", index = 7)
    private Float fat;

    @ExcelProperty(value = "碳水化合物", index = 8)
    private Float carbonWater;

    @ExcelProperty(value = "膳食纤维", index = 9)
    private Float dietaryFiber;

    @ExcelProperty(value = "维生素A", index = 10)
    private Float vitaminA;

    @ExcelProperty(value = "维生素C", index = 11)
    private Float vitaminC;
    @ExcelProperty(value = "维生素E", index = 12)
    private Float vitaminE;
    @ExcelProperty(value = "胡萝卜素", index = 13)
    private Float carrot;
    @ExcelProperty(value = "维生素B1", index = 14)
    private Float vitaminB1;
    @ExcelProperty(value = "维生素B2", index = 15)
    private Float vitaminB2;
    @ExcelProperty(value = "烟酸", index = 16)
    private Float niacin;
    @ExcelProperty(value = "胆固醇", index = 17)
    private Float cholesterol;
    @ExcelProperty(value = "镁", index = 18)
    private Float magnesium;
    @ExcelProperty(value = "铁", index = 19)
    private Float iron;
    @ExcelProperty(value = "钙", index = 20)
    private Float calcium;
    @ExcelProperty(value = "锌", index = 21)
    private Float zinc;
    @ExcelProperty(value = "铜", index = 22)
    private Float copper;
    @ExcelProperty(value = "锰", index = 23)
    private Float manganese;
    @ExcelProperty(value = "钾", index = 24)
    private Float potassium;
    @ExcelProperty(value = "磷", index = 25)
    private Float phosphorus;
    @ExcelProperty(value = "钠", index = 26)
    private Float sodium;
    @ExcelProperty(value = "硒", index = 27)
    private Float selenium;


}

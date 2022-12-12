package com.google.personalhealthrecordingplateform.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;


/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/7 16:37
 */
@Data
public class Food {
    @ExcelIgnore
    @ApiModelProperty("主键")
    private Long id;

    @ExcelProperty(value = "食物类别")
    @Transient
    @JsonIgnore
    private String typeTitle;

    @ExcelProperty(value = "食物名称")
    @Transient
    private String title;


    @ExcelIgnore
    @ApiModelProperty("食物类别")
    private Long typeId;


    //TODO 原始的excel中是图片，但是这里我需要图片的url地址，
    @ExcelProperty(value = "食物图片")
    @ApiModelProperty("图片地址")
    private String imageUrls;

    @ExcelProperty(value = "营养元素")
    @ApiModelProperty("营养元素")
    private String nutrient;

    @ExcelProperty(value = "热量")
    @ApiModelProperty("热量")
    private Float heat;

    @ExcelProperty(value = "蛋白质")
    @ApiModelProperty("蛋白质")
    private Float protein;

    @ExcelProperty(value = "脂肪")
    @ApiModelProperty("脂肪")
    private Float fat;

    @ExcelProperty(value = "碳水化合物")
    @ApiModelProperty("碳水化合物")
    private Float carbonWater;

    @ExcelProperty(value = "膳食纤维")
    @ApiModelProperty("膳食纤维")
    private Float dietaryFiber;


    @ExcelProperty(value = "维生素A")
    @ApiModelProperty("维生素A")
    private Float vitaminA;

    @ExcelProperty(value = "维生素C")
    @ApiModelProperty("维生素C")
    private Float vitaminC;

    @ExcelProperty(value = "维生素E")
    @ApiModelProperty("维生素E")
    private Float vitaminE;

    @ExcelProperty(value = "胡萝卜素")
    @ApiModelProperty("胡萝卜素")
    private Float carrot;

    @ExcelProperty(value = "维生素B1")
    @ApiModelProperty("维生素B1")
    private Float vitaminB1;

    @ExcelProperty(value = "维生素B2")
    @ApiModelProperty("维生素B2")
    private Float vitaminB2;

    @ExcelProperty(value = "烟酸")
    @ApiModelProperty("烟酸")
    private Float niacin;

    @ExcelProperty(value = "胆固醇")
    @ApiModelProperty("胆固醇")
    private Float cholesterol;

    @ExcelProperty(value = "镁")
    @ApiModelProperty("镁")
    private Float magnesium;

    @ExcelProperty(value = "铁")
    @ApiModelProperty("铁")
    private Float iron;

    @ExcelProperty(value = "钙")
    @ApiModelProperty("钙")
    private Float calcium;

    @ExcelProperty(value = "锌")
    @ApiModelProperty("锌")
    private Float zinc;

    @ExcelProperty(value = "铜")
    @ApiModelProperty("铜")
    private Float copper;

    @ExcelProperty(value = "锰")
    @ApiModelProperty("锰")
    private Float manganese;

    @ExcelProperty(value = "钾")
    @ApiModelProperty("钾")
    private Float potassium;

    @ExcelProperty(value = "磷")
    @ApiModelProperty("磷")
    private Float phosphorus;

    @ExcelProperty(value = "钠")
    @ApiModelProperty("钠")
    private Float sodium;

    @ExcelProperty(value = "硒")
    @ApiModelProperty("硒")
    private Float selenium;


}

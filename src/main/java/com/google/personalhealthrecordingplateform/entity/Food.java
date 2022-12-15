package com.google.personalhealthrecordingplateform.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;


/**
 * 对food表进行建模
 *
 * @author W&F
 * @version 1.0
 * @date 2022/12/7 16:37
 */
@Data
public class Food {
    @ApiModelProperty("主键")
    private Long id;

    @Transient
    private String title;

    @ApiModelProperty("食物类别")
    private Long typeId;

    @ApiModelProperty("图片地址")
    private String imageUrls;


    @ApiModelProperty("营养元素")
    private String nutrient;


    @ApiModelProperty("热量")
    private Float heat;


    @ApiModelProperty("蛋白质")
    private Float protein;


    @ApiModelProperty("脂肪")
    private Float fat;


    @ApiModelProperty("碳水化合物")
    private Float carbonWater;


    @ApiModelProperty("膳食纤维")
    private Float dietaryFiber;


    @ApiModelProperty("维生素A")
    private Float vitaminA;


    @ApiModelProperty("维生素C")
    private Float vitaminC;

    @ApiModelProperty("维生素E")
    private Float vitaminE;


    @ApiModelProperty("胡萝卜素")
    private Float carrot;


    @ApiModelProperty("维生素B1")
    private Float vitaminB1;


    @ApiModelProperty("维生素B2")
    private Float vitaminB2;


    @ApiModelProperty("烟酸")
    private Float niacin;


    @ApiModelProperty("胆固醇")
    private Float cholesterol;


    @ApiModelProperty("镁")
    private Float magnesium;

    @ApiModelProperty("铁")
    private Float iron;


    @ApiModelProperty("钙")
    private Float calcium;


    @ApiModelProperty("锌")
    private Float zinc;


    @ApiModelProperty("铜")
    private Float copper;

    @ApiModelProperty("锰")
    private Float manganese;

    @ApiModelProperty("钾")
    private Float potassium;

    @ApiModelProperty("磷")
    private Float phosphorus;

    @ApiModelProperty("钠")
    private Float sodium;

    @ApiModelProperty("硒")
    private Float selenium;


}

package com.google.personalhealthrecordingplateform.dto;

import com.google.personalhealthrecordingplateform.annotation.ExcelImageData;
import com.google.personalhealthrecordingplateform.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/21 15:00
 */
@Data
public class GoodsImportExcelDTO {
    @ExcelProperty(value = "商品名称", index = 1)
    private String name;

    @ExcelProperty(value = "商品价格", index = 2)
    private Float price;

    @ExcelProperty(value = "商品数量", index = 3)
    private Integer number;

    @ExcelImageData
    @ExcelProperty(value = "商品图片", index = 4)
    private String imageUrl;

    @ExcelProperty(value = "适配机型", index = 5)
    private String modelType;

    @ExcelProperty(value = "控制方式", index = 6)
    private String controlMode;

    @ExcelProperty(value = "主要功能", index = 7)
    private String mainFunction;

    @ExcelProperty(value = "无线功能", index = 8)
    private String wifiFunction;

    @ExcelProperty(value = "电池规格", index = 9)
    private String battery;

    @ExcelProperty(value = "特色功能", index = 10)
    private String characteristic;

    @ExcelProperty(value = "外观尺寸", index = 11)
    private String size;

    @ExcelProperty(value = "其他功能", index = 12)
    private String other;

}

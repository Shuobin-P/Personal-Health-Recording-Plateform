package com.google.personalhealthrecordingplateform.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/19 16:55
 */
@Data
public class GoodsDTO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品价格
     */
    private Float price;
    /**
     * 商品数量
     */
    private Integer number;

    /**
     * 商品图片
     */
    private String imageUrl;
    /**
     * 适配机型
     */
    private String modelType;
    /**
     * 控制方式
     */
    private String controlMode;
    /**
     * 主要功能
     */
    private String mainFunction;
    /**
     * 无线功能
     */
    private String wifiFunction;
    /**
     * 电池规格
     */
    private String battery;
    /**
     * 特色功能
     */
    private String characteristic;
    /**
     * 外观尺寸
     */
    private String size;
    /**
     * 其他功能
     */
    private String other;
    /**
     * 备注
     */
    private String remark;

}

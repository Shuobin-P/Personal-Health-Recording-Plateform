package com.google.personalhealthrecordingplateform.entity;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/21 21:20
 */
@Data
public class Sport {
    /**
     * 主键
     */
    private Long id;
    /**
     * 运动名称
     */
    private String name;
    /**
     * 适用年龄
     */
    private String applicableAge;
    /**
     * 身体收益部位
     */
    private String beneficialPosition;
    /**
     * 运动科普
     */
    private String introduction;
}

package com.google.personalhealthrecordingplateform.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/16 12:33
 */
@Data
@ApiModel(value = "运动简介实体")
public class SportNews {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "创建者")
    private String createName;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "更新者")
    private String updateName;

    @ApiModelProperty(value = "删除标记")
    private boolean del;

}

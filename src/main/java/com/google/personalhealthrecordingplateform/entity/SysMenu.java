package com.google.personalhealthrecordingplateform.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 31204
 */
@Data
/**
 * 不同的role有不同的菜单
 */
public class SysMenu implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父级菜单")
    private Long parentId;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "前端路由")
    private String path;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "显示状态(0不显示、1显示)")
    private boolean status;

    @ApiModelProperty(value = "组件名")
    private String component;

    @ApiModelProperty(value = "子菜单")
    private List<SysMenu> children;
}

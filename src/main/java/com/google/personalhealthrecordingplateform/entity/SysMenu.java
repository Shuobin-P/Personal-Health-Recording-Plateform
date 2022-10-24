package com.google.personalhealthrecordingplateform.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysMenu implements Serializable {
    private Long id;

    private Long parent_id;

    private String title;

    private String path;

    private String icon;

    @ApiModelProperty(value = "组件名")
    private String component;

    @ApiModelProperty(value = "子菜单")
    private List<SysMenu> children;
}

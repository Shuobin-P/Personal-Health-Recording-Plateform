package com.google.personalhealthrecordingplateform.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author W&F
 */
@Data
public class SysRole implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "角色代码")
    private String code;

    @ApiModelProperty(value = "状态")
    private boolean status;

    @ApiModelProperty(value = "菜单列表")
    private List<SysMenu> menus;

    @ApiModelProperty(value = "权限列表")
    private List<SysPermission> permissions;

}

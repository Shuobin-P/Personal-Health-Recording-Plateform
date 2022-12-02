package com.google.personalhealthrecordingplateform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author W&F
 */
@Data
@ApiModel(value = "登录参数")
public class LoginVO {
    @ApiModelProperty(value = "用户名", dataType = "java.lang.String")
    private String username;

    @ApiModelProperty(value = "密码", dataType = "java.lang.String")
    private String password;

    private String type;
}

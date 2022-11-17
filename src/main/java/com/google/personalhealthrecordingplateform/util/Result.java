package com.google.personalhealthrecordingplateform.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "响应参数")
public class Result implements Serializable {

    @ApiModelProperty(value = "是否成功标识", dataType = "boolean")
    private boolean flag;
    @ApiModelProperty(value = "响应描述", dataType = "String")
    private String message;
    @ApiModelProperty(value = "响应数据", dataType = "Object")
    private Object data;

    public Result() {
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public static Result success(String message) {
        return new Result(true, message, null);
    }

    public static Result success(String message, Object data) {
        return new Result(true, message, data);
    }

    public static Result fail(String message) {
        return new Result(false, message, null);
    }
}

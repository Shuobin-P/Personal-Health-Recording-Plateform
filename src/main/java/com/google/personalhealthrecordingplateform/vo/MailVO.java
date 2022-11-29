package com.google.personalhealthrecordingplateform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/25 13:30
 */
@Data
@ApiModel(value = "邮件发送内容")
public class MailVO {
    @ApiModelProperty(value = "是否是HTML格式")
    private Boolean html = false;

    @ApiModelProperty(value = "接收人(可以多个)")
    public String[] receivers;

    @ApiModelProperty(value = "邮件主题")
    private String subject;

    @ApiModelProperty(value = "邮件内容")
    private String content;
}

package com.google.personalhealthrecordingplateform.util;

import lombok.Data;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/1/12 18:04
 */
@Data
public class MiniEncryptedData {
    /**
     * 加密数据
     */
    private String encryptedData;

    /**
     * 调用微信运动传递的矢量加密算法
     */
    private String iv;

    /**
     * 微信小程序唯一标志
     */
    private String openid;
}

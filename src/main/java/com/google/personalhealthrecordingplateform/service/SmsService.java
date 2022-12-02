package com.google.personalhealthrecordingplateform.service;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/30 18:13
 */
public interface SmsService {
    /**
     * 发送验证码
     *
     * @param phoneNumber 电话号码
     */
    void sendVerificationCode(String phoneNumber) throws TencentCloudSDKException;
}

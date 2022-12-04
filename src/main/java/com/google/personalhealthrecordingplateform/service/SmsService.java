package com.google.personalhealthrecordingplateform.service;


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
     * @return 短信发送流水号
     */
    void sendVerificationCode(String phoneNumber);
}

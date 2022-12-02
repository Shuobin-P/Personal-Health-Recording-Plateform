package com.google.personalhealthrecordingplateform.service.impl;

import com.google.personalhealthrecordingplateform.service.SmsService;
import com.google.personalhealthrecordingplateform.util.RedisUtils;
import com.google.personalhealthrecordingplateform.util.SmsUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/30 21:08
 */
@Slf4j
public class SmsServiceImpl implements SmsService {
    private SmsClient smsClient;
    private SendSmsRequest smsReq;
    private RedisUtils redisUtils;

    @Autowired
    public SmsServiceImpl(RedisUtils redisUtils, SmsClient smsClient, SendSmsRequest smsReq) {
        this.smsClient = smsClient;
        this.smsReq = smsReq;
        this.redisUtils = redisUtils;
    }


    @Override
    public void sendVerificationCode(String phoneNumber) throws TencentCloudSDKException {
        //要运营商向指定电话号码发送包含验证码的短信，并把电话号码和验证码存储到redis中，并设置5分钟的期限
        //参考：https://cloud.tencent.com/document/product/382/43194
        phoneNumber = "+86" + phoneNumber;
        String vcode = SmsUtils.getVerificationCode();
        smsReq.setTemplateParamSet(new String[]{vcode, "5"});
        smsReq.setPhoneNumberSet(new String[]{phoneNumber});
        SendSmsResponse res = smsClient.SendSms(smsReq);
        //把电话号码和验证码存入redis中
        redisUtils.add("java_sport:sys_user:" + phoneNumber, vcode);
        redisUtils.setExpiration("java_sport:sys_user:" + phoneNumber, 300);
        log.info(SendSmsResponse.toJsonString(res));
    }
}

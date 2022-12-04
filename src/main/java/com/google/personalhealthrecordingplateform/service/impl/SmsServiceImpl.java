package com.google.personalhealthrecordingplateform.service.impl;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.google.personalhealthrecordingplateform.service.SmsService;
import com.google.personalhealthrecordingplateform.util.RedisUtils;
import com.google.personalhealthrecordingplateform.util.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/30 21:08
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    private Client client;
    private RedisUtils redisUtils;

    @Value("${sms.signName}")
    private String signName;

    @Value("${sms.templateId}")
    private String templateId;

    public SmsServiceImpl(Client client, RedisUtils redisUtils) {
        this.client = client;
        this.redisUtils = redisUtils;
    }


    @Override
    public void sendVerificationCode(String phoneNumber) {
        // 初始化 Client，采用 AK&SK 鉴权访问的方式，此方式可能会存在泄漏风险，建议使用 STS 方式。鉴权访问方式请参考：https://help.aliyun.com/document_detail/378657.html
        // 获取 AK 链接：https://usercenter.console.aliyun.com
        String vcode = SmsUtils.getVerificationCode();
        redisUtils.add("java_sport:sys_user:phone_number:" + phoneNumber, vcode);
        redisUtils.setExpiration("java_sport:sys_user:phone_number:" + phoneNumber, 300);
        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setSignName(signName)
                .setTemplateCode(templateId)
                .setPhoneNumbers(phoneNumber)
                .setTemplateParam("{\"code\":\"" + vcode + "\"}");

        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            client.sendSmsWithOptions(sendSmsRequest, runtime);
        } catch (TeaException error) {
            System.out.println(error);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            System.out.println(error);
        }
    }
}

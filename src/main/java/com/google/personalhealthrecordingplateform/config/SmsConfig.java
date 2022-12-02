package com.google.personalhealthrecordingplateform.config;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/30 21:22
 */
@Configuration
public class SmsConfig {

    @Value("${sms.secretId}")
    private String secretId;

    @Value("${sms.secretKey}")
    private String secretKey;

    @Value("${sms.sdkAppId}")
    private String sdkAppId;

    @Value("${sms.signName}")
    private String signName;

    @Value("${sms.templateId}")
    private String templateId;


    @Bean
    public Credential getCredential() {
        return new Credential(secretId, secretKey);
    }

    @Bean
    public HttpProfile getHttpProfile() {
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        return httpProfile;
    }

    @Bean
    public ClientProfile getClientProfile(HttpProfile httpProfile) {
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return clientProfile;
    }

    @Bean
    public SmsClient getSmsClient(Credential cred, ClientProfile clientProfile) {
        return new SmsClient(cred, "ap-guangzhou", clientProfile);
    }

    @Bean
    public SendSmsRequest getSendSmsRequest() {
        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppId(sdkAppId);
        req.setSignName(signName);
        req.setTemplateId(templateId);
        return req;
    }
}

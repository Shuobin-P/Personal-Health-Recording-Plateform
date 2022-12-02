package com.google.personalhealthrecordingplateform;

import com.google.personalhealthrecordingplateform.service.SmsService;
import com.google.personalhealthrecordingplateform.service.impl.SmsServiceImpl;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import org.junit.jupiter.api.Test;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/1 11:17
 */
public class SmsServiceTest {
    String sdkAppId = "1400773921";
    String signName = "彬哥的小站公众号";
    String templateId = "1624005";
    //SmsService smsService = new SmsServiceImpl(new SendSmsRequest(), new SmsClient(null, null));

    @Test
    public void sendVerificationCodeTest() throws TencentCloudSDKException {
        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppId(sdkAppId);
        req.setSignName(signName);
        req.setTemplateId(templateId);
        //smsService.sendVerificationCode("");
    }
}

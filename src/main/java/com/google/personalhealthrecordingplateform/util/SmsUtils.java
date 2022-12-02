package com.google.personalhealthrecordingplateform.util;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/30 17:43
 */
public class SmsUtils {

    /**
     * 生成6位随机数验证码
     *
     * @return 验证码
     */
    public static String getVerificationCode() {
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        return vcode;
    }
}

package com.google.personalhealthrecordingplateform;

import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/22 18:52
 */
public class QiniuTest {
    String accessKey = "VjqyGJIWeKMORqLsJR-3CMSBiKTxYF25wC5CSPbc";
    String secretKey = "adec1oMqUWZAUKPruXLwus2822HK9wMJwpTllO8J";
    String bucket = "healthy-plateform-images";

    @Test
    public void test() {
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucket);
        System.out.println(token);
    }

}

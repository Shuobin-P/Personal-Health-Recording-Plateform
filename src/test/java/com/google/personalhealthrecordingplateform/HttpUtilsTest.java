package com.google.personalhealthrecordingplateform;

import com.google.personalhealthrecordingplateform.util.HttpUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/1/13 12:32
 */
public class HttpUtilsTest {

    @Test
    public void sendGetTest() throws URISyntaxException, IOException {
        CloseableHttpResponse response = HttpUtils.sendGet("https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLGkQk78eWs9HRfGWibjW0ZCXzHHCH5jhBicrWOexYxaIyABlyK8ibHUzaibibOQ5AosTrbdJKDnM0WFjg/132");
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\Test\\test.png");
        if (response.getStatusLine().getStatusCode() == 200) {
            inputStream = response.getEntity().getContent();
            int by = inputStream.read();
            while (by != -1) {
                fileOutputStream.write(by);
                by = inputStream.read();
            }

        }
        inputStream.close();
        fileOutputStream.close();
    }
}

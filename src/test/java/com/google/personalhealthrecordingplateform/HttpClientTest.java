package com.google.personalhealthrecordingplateform;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/23 16:17
 */
@Slf4j
public class HttpClientTest {
    @Test
    public void getTest() throws URISyntaxException, IOException {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 定义请求的参数
        URI uri = new URIBuilder("https://api.weixin.qq.com/sns/jscode2session")
                .setParameter("appid", "wxfb9f6a157d4f2458")
                .setParameter("secret", "37a056dbf465b9ed3b202c998722feec")
                .setParameter("js_code", "031hH4Ga1arXtE0W8CIa1xww1Y2hH4Gd")
                .setParameter("grant_type", "authorization_code")
                .setParameters()
                .build();
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(uri);
        //response 对象
        CloseableHttpResponse response = null;
        try {
            // 执行http get请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                Map<String, Object> map = this.httpEntityToMap(response.getEntity());
                //内容写入文件
                if (map.get("errcode") == null) {
                    System.out.println("Session_Key为：" + map.get("session_key"));
                } else {
                    log.info("错误信息：" + map.get("errmsg"));
                }
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }

    public static Map httpEntityToMap(HttpEntity httpEntity) throws IOException {
        //先把HttpEntity转为字符串，再把字符串转为json对象
        JSONObject object = new JSONObject().parseObject(EntityUtils.toString(httpEntity));
        Map<String, Object> map = new HashMap<>();
        //循环转换
        Iterator it = object.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}


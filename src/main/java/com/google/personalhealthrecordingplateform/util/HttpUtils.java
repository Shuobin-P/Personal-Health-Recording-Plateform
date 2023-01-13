package com.google.personalhealthrecordingplateform.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/23 17:20
 */
public class HttpUtils {
    public static CloseableHttpResponse sendGet(String url) throws URISyntaxException, IOException {
        CloseableHttpClient httpclient = null;
        try {
            httpclient = HttpClients.createDefault();
            URI uri = new URIBuilder(url).build();
            HttpGet httpGet = new HttpGet(uri);
            return httpclient.execute(httpGet);
        } catch (Exception e) {
            throw e;
        } finally {
            httpclient.close();
        }

    }

    public static CloseableHttpResponse sendGet(String url, List<NameValuePair> paramPairList) throws URISyntaxException, IOException {
        CloseableHttpClient httpclient = null;
        try {
            httpclient = HttpClients.createDefault();
            URI uri = new URIBuilder(url)
                    .setParameters(paramPairList)
                    .build();
            HttpGet httpGet = new HttpGet(uri);
            return httpclient.execute(httpGet);
        } catch (Exception e) {
            throw e;
        } finally {
            httpclient.close();
        }
    }

    public static Map<String, Object> transferHttpEntityToMap(HttpEntity httpEntity) throws IOException {
        //先把HttpEntity转为字符串，再把字符串转为json对象
        new JSONObject();
        JSONObject object = JSON.parseObject(EntityUtils.toString(httpEntity));
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

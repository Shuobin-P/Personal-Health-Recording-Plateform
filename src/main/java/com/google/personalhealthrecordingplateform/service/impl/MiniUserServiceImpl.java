package com.google.personalhealthrecordingplateform.service.impl;

import com.google.personalhealthrecordingplateform.service.MiniUserService;
import com.google.personalhealthrecordingplateform.util.HttpUtils;
import com.google.personalhealthrecordingplateform.util.RedisUtils;
import com.google.personalhealthrecordingplateform.util.Result;
import com.google.personalhealthrecordingplateform.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/22 16:11
 */
@Slf4j
@Service
public class MiniUserServiceImpl implements MiniUserService {

    @Value(value = "${mini.appid}")
    private String appid;

    @Value(value = "${mini.appsecret}")
    private String secret;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    private final TokenUtils tokenUtils;
    private final RedisUtils redisUtils;
    private UserDetailsService userDetailsService;

    @Autowired
    public MiniUserServiceImpl(TokenUtils tokenUtils, RedisUtils redisUtils, @Qualifier("userDetailsServiceImp") UserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
        this.redisUtils = redisUtils;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public Result login(String code) throws IOException {
        //用户唯一标识
        String openid;
        //会话密钥
        String session_key;
        UserDetails userDetails;
        CloseableHttpResponse response = null;
        Map<String, Object> responsePairMap;
        List<NameValuePair> list = new ArrayList<>();
        BasicNameValuePair pair0 = new BasicNameValuePair("appid", this.appid);
        BasicNameValuePair pair1 = new BasicNameValuePair("secret", this.secret);
        BasicNameValuePair pair2 = new BasicNameValuePair("js_code", code);
        BasicNameValuePair pair3 = new BasicNameValuePair("grant_type", "authorization_code");
        list.add(pair0);
        list.add(pair1);
        list.add(pair2);
        list.add(pair3);
        try {
            response = HttpUtils.sendGet("https://api.weixin.qq.com/sns/jscode2session", list);
            responsePairMap = HttpUtils.transferHttpEntityToMap(response.getEntity());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("服务器内部错误，登录失败");
        } finally {
            if (response != null) {
                response.close();
            }
        }
        if (responsePairMap.get("errcode") == null) {
            session_key = (String) responsePairMap.get("session_key");
            openid = (String) responsePairMap.get("openid");
        } else {
            log.info("错误信息：" + responsePairMap.get("errmsg"));
            return Result.fail("登录失败" + responsePairMap.get("errmsg"));
        }
        //现在我服务器拿到了用户的openid，就可以生成jwt了，但是这个session_key似乎没啥作用。
        //后序前端访问我这个服务器的时候，携带jwt就可以了，session_key似乎还是没有作用，
        userDetails = userDetailsService.loadUserByUsername(openid);
        redisUtils.add("java_sport:sys_user:open_id:" + openid, session_key);
        Map<String, Object> map = new HashMap<>();
        map.put("username", openid);
        map.put("created", new Date());
        String token = tokenUtils.generateToken(map);
        log.info("小程序JWT:" + token);
        Map<String, Object> tokenMap = new HashMap<>(2);
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("token", token);
        tokenMap.put("openid", openid);
        tokenMap.put("userInfo", userDetails);
        return Result.success("小程序用户登录成功", tokenMap);
    }
}

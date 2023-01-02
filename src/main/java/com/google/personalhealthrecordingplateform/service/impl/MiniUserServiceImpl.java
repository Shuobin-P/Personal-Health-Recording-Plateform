package com.google.personalhealthrecordingplateform.service.impl;

import com.google.personalhealthrecordingplateform.common.MiniOpenIDAuthentication;
import com.google.personalhealthrecordingplateform.service.MiniUserService;
import com.google.personalhealthrecordingplateform.util.HttpUtils;
import com.google.personalhealthrecordingplateform.util.Result;
import com.google.personalhealthrecordingplateform.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
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

    private final UserDetailsService userDetailsService;

    @Autowired
    public MiniUserServiceImpl(TokenUtils tokenUtils, @Qualifier("miniUserDetailsServiceImp") UserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
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
        userDetails = userDetailsService.loadUserByUsername(openid);
        //TODO 这里使用什么类型的authentication取决于登录的方式，如果登录方式选择username password就选择usernamePasswordAuthenticationToken
        // 选择不同类型的authentication肯定是有区别的，搞清楚微信小程序这种方式是属于哪种登录方式? OAuth2.0
        //FIXME SpringSecurity好像不支持这种authentication
        //FIXME 其实可以使用redis来保存这个session_key，因为那个JWT中不是保存了open_id麻？正好是一个键值对，保存到redis里面
        Authentication authentication = new MiniOpenIDAuthentication(userDetails, null, session_key);
        //后台管理系统也是直接这样放进去的，但是小程序这边就不能使用了，就是因为自己这个authentication有问题吧。
        //TODO 研究一下setAuthentication()吧
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("为小程序用户创建authentication,open_id= " + userDetails.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("username", openid);
        map.put("created", new Date());
        String token = tokenUtils.generateToken(map);
        log.info("小程序JWT:" + token);
        Map<String, String> tokenMap = new HashMap<>(2);
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("token", token);
        return Result.success("小程序用户登录成功", tokenMap);
    }
}

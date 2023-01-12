package com.google.personalhealthrecordingplateform.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.entity.WxRun;
import com.google.personalhealthrecordingplateform.mapper.SysUserMapper;
import com.google.personalhealthrecordingplateform.mapper.WxRunMapper;
import com.google.personalhealthrecordingplateform.service.MiniUserService;
import com.google.personalhealthrecordingplateform.util.*;
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
import org.springframework.transaction.annotation.Transactional;

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
    private final DateUtils dateUtils;
    private final DecryptDataUtils decryptDataUtils;
    private final WxRunMapper wxRunMapper;
    private final SysUserMapper sysUserMapper;
    private final UserDetailsService userDetailsService;

    @Autowired
    public MiniUserServiceImpl(TokenUtils tokenUtils, RedisUtils redisUtils, DateUtils dateUtils, DecryptDataUtils decryptDataUtils, WxRunMapper wxRunMapper, SysUserMapper sysUserMapper, @Qualifier("userDetailsServiceImp") UserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
        this.redisUtils = redisUtils;
        this.dateUtils = dateUtils;
        this.decryptDataUtils = decryptDataUtils;
        this.wxRunMapper = wxRunMapper;
        this.sysUserMapper = sysUserMapper;
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

    @Override
    public Result logout(String authorizationHeaderVal) {
        String token = tokenUtils.extractToken(authorizationHeaderVal);
        try {
            redisUtils.delete("java_sport:sys_user:open_id:" + tokenUtils.getTokenBody(token).get("open_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("退出登录失败");
        }
        return Result.success("成功退出登录");
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Result getTodaySteps(String encryptedData, String iv, String openId) {
        String stepsData = decryptDataUtils.decryptData(encryptedData, (String) redisUtils.get("java_sport:sys_user:open_id:" + openId), iv);
        JSONObject result = JSONObject.parseObject(stepsData);
        JSONArray stepInfoList = result.getJSONArray("stepInfoList");
        List<WxRun> steps = new ArrayList<>();
        for (Object o : stepInfoList) {
            JSONObject obj = (JSONObject) o;
            Long timestamp = obj.getLong("timestamp");
            WxRun step = new WxRun(openId, dateUtils.transformTimestampToDate(timestamp), obj.getInteger("step"));
            steps.add(step);
        }
        //遍历steps，若数据库中，有该记录，则更新，否则，放入temp，最后Mybatis批量插入
        List<WxRun> temp = new ArrayList<>();
        for (WxRun e : steps) {
            WxRun wxStep = wxRunMapper.find(e.getOpenid(), e.getTime());
            if (wxStep != null) {
                wxRunMapper.update(e);
            } else {
                temp.add(e);
            }
        }
        //FIXME 为什么不能批量插入 wxRunMapper.batchInsert(temp);
        temp.stream().forEach(e -> wxRunMapper.insert(e));
        return Result.success("获取今日步数", steps.get(steps.size() - 1).getStep());
    }

    @Override
    public Result getRecentFourWeeksSteps(String authorizationHeaderVal) {
        Map<String, List<WxRun>> map = new HashMap<>(4);
        //本周的数据
        String openID = tokenUtils.getUsernameByToken(tokenUtils.extractToken(authorizationHeaderVal));
        int days = dateUtils.getDayIndexOfWeek(new Date());
        List<WxRun> thisWeekSteps = new ArrayList<>();
        for (int i = days - 1; i >= 0; i--) {
            String date = dateUtils.parseDate(dateUtils.getDateBeforeORAfterToday(-i), "yyyy-MM-dd");
            WxRun wxStep = wxRunMapper.find(openID, date);
            thisWeekSteps.add(wxStep);
            if (wxStep == null) {
                System.out.println("微信步数为空");
            }
            System.out.println("openID: " + openID + "Date: " + date + "Steps: ");
        }
        map.put("week1", thisWeekSteps);
        //前面三周的数据
        Date lastSunday = dateUtils.getDateBeforeORAfterToday(-days);
        for (int i = 1; i <= 3; i++) {
            List<WxRun> list = new ArrayList<>();
            for (int j = 6; j >= 0; j--) {
                String date = dateUtils.parseDate(
                        dateUtils.getDateBeforeORAfterSpecificDate(lastSunday, -j), "yyyy-MM-dd");
                WxRun wxStep = wxRunMapper.find(openID, date);
                list.add(wxStep);
            }
            //lastSunday的上一个Sunday
            lastSunday = dateUtils.getDateBeforeORAfterSpecificDate(lastSunday, -7);
            map.put("week" + (i + 1), list);
        }
        return Result.success("最近四周的数据", map);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Result updateInfoByOpenId(SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getOpenId())) {
            return Result.fail("请传递小程序用户唯一标识open_id");
        }
        redisUtils.delete("java_sport:sys_user:" + sysUser.getOpenId());
        sysUserMapper.updateInfoByOpenId(sysUser);
        return Result.success("成功更新用户信息");
    }


}

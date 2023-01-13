package com.google.personalhealthrecordingplateform.service;

import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.util.Result;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/22 16:09
 */
public interface MiniUserService {
    /**
     * 登录
     *
     * @param code 从微信接口获得的code，传递给后端，后端得到获取open_id,session_key
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    Result login(String code) throws URISyntaxException, IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * 退出登录
     *
     * @param authorizationHeaderVal Authorization Header的值
     * @return
     */
    Result logout(String authorizationHeaderVal);


    Result getTodaySteps(String encryptedData, String iv, String openId);


    Result getRecentFourWeeksSteps(String authorizationHeaderVal);


    /**
     * 通过openid更新用户信息
     *
     * @param sysUser
     * @return
     */
    Result updateInfoByOpenId(SysUser sysUser) throws URISyntaxException, IOException;
}

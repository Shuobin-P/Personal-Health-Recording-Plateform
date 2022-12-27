package com.google.personalhealthrecordingplateform.service;

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
     * 小程序用户登录接口
     *
     * @param code
     * @return
     */
    Result login(String code) throws URISyntaxException, IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}

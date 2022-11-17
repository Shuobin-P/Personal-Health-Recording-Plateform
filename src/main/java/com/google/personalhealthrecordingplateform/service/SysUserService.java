package com.google.personalhealthrecordingplateform.service;

import com.google.personalhealthrecordingplateform.util.Result;
import com.google.personalhealthrecordingplateform.vo.LoginVo;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 这个接口有必要继承UserDetailsService吗？
 *
 * @author W&F
 */
public interface SysUserService extends UserDetailsService {

    /**
     * 获取所有用户的信息
     *
     * @return 所有的用户信息
     */
    Result findAll();

    /**
     * 通过用户ID查询用户信息
     *
     * @param id 用户ID
     * @return 该用户的信息
     */
    Result selectByID(Integer id);

    /**
     * 用户登录
     *
     * @param loginVo 用户提交的登录信息
     * @return 用户身份验证结果
     */
    Result login(LoginVo loginVo);

}

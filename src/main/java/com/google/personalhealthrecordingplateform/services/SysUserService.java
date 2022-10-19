package com.google.personalhealthrecordingplateform.services;

import com.google.personalhealthrecordingplateform.utils.Result;
import com.google.personalhealthrecordingplateform.vo.LoginVo;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 这个接口有必要继承UserDetailsService吗？
 */
public interface SysUserService extends UserDetailsService {

    Result findAll();

    Result selectByID(Integer id);

    Result login(LoginVo loginVo);

}

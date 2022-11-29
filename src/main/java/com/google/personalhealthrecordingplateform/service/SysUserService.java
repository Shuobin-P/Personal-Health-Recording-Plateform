package com.google.personalhealthrecordingplateform.service;

import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;
import com.google.personalhealthrecordingplateform.vo.LoginVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * 这个接口有必要继承UserDetailsService吗？
 *
 * @author W&F
 */
public interface SysUserService extends UserDetailsService {

    /**
     * 插入用户数据
     *
     * @param sysUser 用户
     * @return 插入用户信息结果
     */
    Result insert(SysUser sysUser);

    /**
     * 删除用户
     *
     * @param userID 用户ID
     */
    void delete(Long userID);

    /**
     * 修改用户信息
     *
     * @param sysUser 用户信息
     * @return 修改结果
     */
    Result update(SysUser sysUser);


    /**
     * 获取所有用户的信息
     *
     * @return 所有的用户信息
     */
    Result findAll();

    /**
     * 分页查询用户信息
     *
     * @param queryInfo 模糊查询信息
     * @return 满足条件的用户信息
     */
    List<SysUser> findPage(QueryInfo queryInfo);

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
    Result login(LoginVO loginVo);

    /**
     * 得到用户的头像文件名
     *
     * @param id 用户ID
     * @return 头像文件名
     */
    String findAvatar(Long id);

    /**
     * 查找数据库中是否包含该邮箱账号对应的用户记录
     *
     * @param email 邮箱
     * @return true OR false
     */
    SysUser findUser(String email);


}

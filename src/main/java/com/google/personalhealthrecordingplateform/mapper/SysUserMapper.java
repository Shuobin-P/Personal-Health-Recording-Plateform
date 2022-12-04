package com.google.personalhealthrecordingplateform.mapper;

import com.google.personalhealthrecordingplateform.entity.SysMenu;
import com.google.personalhealthrecordingplateform.entity.SysPermission;
import com.google.personalhealthrecordingplateform.entity.SysRole;
import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author W&F
 */
@Mapper
public interface SysUserMapper {

    /**
     * 插入用户信息
     *
     * @param sysUser 用户信息
     */
    void insert(SysUser sysUser);

    /**
     * 插入用户对应的role
     *
     * @param userID 用户ID
     * @param roleID 角色ID
     */
    void insertRole(Long userID, Long roleID);

    /**
     * 删除用户信息
     *
     * @param id 用户ID
     */
    void delete(Long id);

    /**
     * 删除用户对应的角色
     *
     * @param userID 用户ID
     */
    void deleteRole(Long userID);

    /**
     * 修改用户信息
     *
     * @param sysUser 用户信息
     */
    void update(SysUser sysUser);

    List<SysUser> findAll();

    SysUser selectByID(Integer userID);

    SysUser findUserByUserName(String username);

    List<SysRole> findRoles(Long userID);

    List<SysMenu> findMenus(Long userID);

    /**
     * 根据父级ID和用户ID查询子菜单
     *
     * @param parentID
     * @param userID
     * @return
     */
    List<SysMenu> findChildrenMenus(Long parentID, Long userID);

    List<SysPermission> findPermissions(Long userID);

    /**
     * 分页查询用户信息
     *
     * @param queryInfo 查询信息
     * @return 符合条件的用户信息
     */
    List<SysUser> findPage(QueryInfo queryInfo);

    /**
     * 得到用户的头像文件名
     *
     * @param id 用户ID
     * @return 头像文件名
     */
    String findAvatar(Long id);

    /**
     * 通过邮箱找到对应的用户信息
     *
     * @param email 邮件地址
     * @return 用户信息
     */
    SysUser findUserByEmail(String email);

    /**
     * 通过电话号码找到对应的用户信息
     *
     * @param phoneNumber 电话号码
     * @return 用户信息
     */
    SysUser findUserByPhoneNumber(String phoneNumber);

    /**
     * 通过电话号码找到对应的用户名
     *
     * @param phoneNumber 电话号码
     * @return 用户名
     */
    String findUserNameByPhoneNumber(String phoneNumber);
}

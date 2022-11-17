package com.google.personalhealthrecordingplateform.mapper;

import com.google.personalhealthrecordingplateform.entity.SysMenu;
import com.google.personalhealthrecordingplateform.entity.SysPermission;
import com.google.personalhealthrecordingplateform.entity.SysRole;
import com.google.personalhealthrecordingplateform.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {

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

}

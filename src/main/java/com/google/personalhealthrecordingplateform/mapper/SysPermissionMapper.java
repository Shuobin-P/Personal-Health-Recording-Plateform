package com.google.personalhealthrecordingplateform.mapper;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限数据的增删改查
 */
@Mapper
public interface SysPermissionMapper {
    //不同的角色(role)有不同的(Permission)
    //不同的角色(role)有不同的菜单
    void insert(SysPermission sysPermission);

    void delete(Long id);

    void update(SysPermission sysPermission);

    Page<SysPermission> findPage(String queryInfo);

}

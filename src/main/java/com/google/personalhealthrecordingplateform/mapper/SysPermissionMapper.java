package com.google.personalhealthrecordingplateform.mapper;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 权限数据的增删改查
 *
 * @author W&F
 */
@Mapper
public interface SysPermissionMapper {
    //不同的角色(role)有不同的(Permission)
    //不同的角色(role)有不同的菜单

    /**
     * 插入权限数据信息
     *
     * @param sysPermission 权限数据
     */
    void insert(SysPermission sysPermission);

    /**
     * 删除权限数据信息
     *
     * @param id 权限数据ID
     */
    void delete(Long id);

    /**
     * 更新权限数据信息
     *
     * @param sysPermission 更新后的权限数据信息
     */
    void update(SysPermission sysPermission);

    /**
     * 分页查询
     *
     * @param queryInfo 模糊查询信息
     * @return
     */
    Page<SysPermission> findPage(String queryInfo);

    /**
     * 查询所有权限信息
     *
     * @return 权限信息列表
     */
    List<SysPermission> findAll();

    /**
     * 通过角色ID查找对应的权限数据
     *
     * @param id 角色ID
     * @return 角色对应的权限数据
     */
    List<SysPermission> findPermissionByRoleID(Long id);

}

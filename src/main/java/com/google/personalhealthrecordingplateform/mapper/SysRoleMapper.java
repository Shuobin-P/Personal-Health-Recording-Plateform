package com.google.personalhealthrecordingplateform.mapper;

import com.google.personalhealthrecordingplateform.entity.SysRole;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/10/26 22:46
 */

@Mapper
public interface SysRoleMapper {
    /**
     * 插入角色数据信息
     *
     * @param sysRole 角色数据
     */
    void insert(SysRole sysRole);

    /**
     * 插入roleId,menuId
     *
     * @param roleId
     * @param menuId
     */
    void insertMenus(Long roleId, Long menuId);

    /**
     * 插入roleId,permissionId
     *
     * @param roleId
     * @param permissionId
     */
    void insertPermissions(Long roleId, Long permissionId);

    /**
     * 删除角色数据信息
     *
     * @param id 角色数据ID
     */
    void delete(Long id);

    /**
     * 删除角色对应的菜单信息
     *
     * @param id
     */
    void deleteMenus(Long id);

    /**
     * 删除角色对应的权限信息
     *
     * @param id
     */
    void deletePermissions(Long id);

    /**
     * 更新角色数据信息
     *
     * @param sysRole 更新后的角色数据信息
     */
    void update(SysRole sysRole);

    /**
     * 分页查询
     *
     * @param queryInfo 模糊查询信息
     * @return 分页查询结果
     */
    List<SysRole> findPage(QueryInfo queryInfo);

    /**
     * 通过label来查询角色(role)
     *
     * @param label
     * @return
     */
    SysRole findRoleByLabel(String label);

    /**
     * 查询所有角色信息
     *
     * @return 所有角色信息
     */
    List<SysRole> findAll();


}

package com.google.personalhealthrecordingplateform.service;

import com.google.personalhealthrecordingplateform.entity.SysPermission;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;

import java.util.List;

/**
 * @author W&F
 */
public interface SysPermissionService {
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
     * @return 分页查询结果
     */
    Result findPage(QueryInfo queryInfo);

    /**
     * 查询所有的权限信息
     *
     * @return
     */
    List<SysPermission> findAll();
}

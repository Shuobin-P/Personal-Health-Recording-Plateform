package com.google.personalhealthrecordingplateform.service;

import com.google.personalhealthrecordingplateform.entity.SysRole;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/10/26 22:53
 */
public interface SysRoleService {
    /**
     * 插入角色数据信息
     *
     * @param sysRole 角色数据
     * @return
     */
    Result insert(SysRole sysRole);

    /**
     * 删除角色数据信息
     *
     * @param id 角色数据ID
     */
    void delete(Long id);

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
     * 查询所有角色信息
     *
     * @return 所有角色信息
     */
    List<SysRole> findAll();
}

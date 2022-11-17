package com.google.personalhealthrecordingplateform.service;

import com.google.personalhealthrecordingplateform.entity.SysMenu;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;

/**
 * @author W&F
 */
public interface SysMenuService {
    /**
     * 插入一个菜单信息
     *
     * @param sysMenu 菜单
     */
    void insert(SysMenu sysMenu);

    /**
     * 删除id对应的菜单
     *
     * @param id 菜单对应的id
     */
    void delete(Long id);

    /**
     * 更新菜单信息
     *
     * @param sysMenu 更新以后的菜单信息
     */
    void update(SysMenu sysMenu);

    /**
     * 找到所有父级菜单
     *
     * @return 父级菜单信息
     */
    Result findParents();

    /**
     * 分页查询
     *
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);
}

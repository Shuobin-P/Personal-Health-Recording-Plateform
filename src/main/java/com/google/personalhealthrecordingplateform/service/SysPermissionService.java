package com.google.personalhealthrecordingplateform.service;

import com.google.personalhealthrecordingplateform.entity.SysPermission;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;

public interface SysPermissionService {
    void insert(SysPermission sysPermission);

    void delete(Long id);

    void update(SysPermission sysPermission);

    /**
     * 分页查询
     *
     * @param queryInfo
     * @return
     */
    Result findPage(QueryInfo queryInfo);
}

package com.google.personalhealthrecordingplateform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.personalhealthrecordingplateform.entity.SysPermission;
import com.google.personalhealthrecordingplateform.mapper.SysPermissionMapper;
import com.google.personalhealthrecordingplateform.service.SysPermissionService;
import com.google.personalhealthrecordingplateform.util.PageResult;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    private final SysPermissionMapper sysPermissionMapper;

    @Autowired
    public SysPermissionServiceImpl(SysPermissionMapper sysPermissionMapper) {
        this.sysPermissionMapper = sysPermissionMapper;
    }

    @Override
    public void insert(SysPermission sysPermission) {
        sysPermissionMapper.insert(sysPermission);

    }

    @Override
    public void delete(Long id) {
        sysPermissionMapper.delete(id);
    }

    @Override
    public void update(SysPermission sysPermission) {
        sysPermissionMapper.update(sysPermission);
    }

    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始权限数据分页-->页码{}, --->{}页数--->查询内容{}", queryInfo.getPageNumber(), queryInfo.getPageSize(), queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        //模糊查询得到的所有数据
        Page<SysPermission> page = sysPermissionMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysPermission> result = page.getResult();
        log.info("查询的总条数-->{}", total);
        log.info("分页列表-->{}", result);
        return new PageResult(total, result);
    }
}

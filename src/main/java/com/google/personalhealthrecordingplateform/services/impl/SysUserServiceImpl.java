package com.google.personalhealthrecordingplateform.services.impl;

import com.google.personalhealthrecordingplateform.mapper.SysUserMapper;
import com.google.personalhealthrecordingplateform.services.SysUserService;
import com.google.personalhealthrecordingplateform.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Result findAll() {
        return Result.success("查询成功", sysUserMapper.findAll());
    }

    @Override
    public Result selectByID(Integer id) {
        return Result.success("找到一条数据", sysUserMapper.selectByID(3));
    }
}

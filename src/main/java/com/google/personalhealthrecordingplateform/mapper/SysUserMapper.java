package com.google.personalhealthrecordingplateform.mapper;

import com.google.personalhealthrecordingplateform.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {

    List<SysUser> findAll();

    SysUser selectByID(Integer id);
}

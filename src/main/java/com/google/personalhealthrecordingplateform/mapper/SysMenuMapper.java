package com.google.personalhealthrecordingplateform.mapper;

import com.github.pagehelper.Page;
import com.google.personalhealthrecordingplateform.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    void insert(SysMenu sysMenu);

    void delete(Long id);

    void update(SysMenu sysMenu);

    List<SysMenu> findParents();

    Page<SysMenu> findPage(String queryInfo);
}

package com.google.personalhealthrecordingplateform.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.personalhealthrecordingplateform.entity.SysMenu;
import com.google.personalhealthrecordingplateform.mapper.SysMenuMapper;
import com.google.personalhealthrecordingplateform.service.SysMenuService;
import com.google.personalhealthrecordingplateform.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author W&F
 */
@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {
    private final RedisUtils redisUtils;
    private final SysMenuMapper sysMenuMapper;


    @Autowired
    public SysMenuServiceImpl(RedisUtils redisUtils, SysMenuMapper sysMenuMapper) {
        this.redisUtils = redisUtils;
        this.sysMenuMapper = sysMenuMapper;
    }

    @Override
    public void insert(SysMenu sysMenu) {
        sysMenuMapper.insert(sysMenu);
        redisUtils.delete("java_sport:sys_user:" + SecurityUtils.getUsername());
    }

    @Override
    public void delete(Long id) {
        sysMenuMapper.delete(id);
        //这个已经登录的人，再把自己的某一项菜单删除掉，那肯定就要把redis中的数据删掉啊。
        redisUtils.delete("java_sport:sys_user:" + SecurityUtils.getUsername());
    }

    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
        redisUtils.delete("java_sport:sys_user:" + SecurityUtils.getUsername());
    }

    @Override
    public Result findParents() {
        //首先从redis中找，如果找到了，则从redis中拿到数据
        //如果redis中没有数据，则先从数据库拿到数据，并把数据写入到redis中
        List<SysMenu> list = (List<SysMenu>) redisUtils.get("java_sport:sys_menu:1");
        if (list == null) {
            list = sysMenuMapper.findParents();
            redisUtils.add("java_sport:sys_menu:1", list);
            redisUtils.setExpiration("java_sport:sys_menu:1", 600);
        }
        return Result.success("成功查询到所有父级菜单", list);
    }

    /**
     * 对父级菜单进行模糊查询
     *
     * @param queryInfo 模糊查询信息
     * @return 模糊查询结果
     */
    @Override
    public Result findPage(QueryInfo queryInfo) {
        log.info("开始权限数据分页-->页码{}, --->{}页数--->查询内容{}", queryInfo.getPageNumber(), queryInfo.getPageSize(), queryInfo.getQueryString());
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        //模糊查询得到的所有数据
        Page<SysMenu> page = sysMenuMapper.findPage(queryInfo.getQueryString());
        long total = page.getTotal();
        List<SysMenu> result = page.getResult();
        log.info("查询的总条数-->{}", total);
        log.info("分页列表-->{}", result);
        return new PageResult(total, result);
    }
}

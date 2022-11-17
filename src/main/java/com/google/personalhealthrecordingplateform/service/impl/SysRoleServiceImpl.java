package com.google.personalhealthrecordingplateform.service.impl;

import com.google.personalhealthrecordingplateform.entity.SysMenu;
import com.google.personalhealthrecordingplateform.entity.SysPermission;
import com.google.personalhealthrecordingplateform.entity.SysRole;
import com.google.personalhealthrecordingplateform.mapper.SysMenuMapper;
import com.google.personalhealthrecordingplateform.mapper.SysPermissionMapper;
import com.google.personalhealthrecordingplateform.mapper.SysRoleMapper;
import com.google.personalhealthrecordingplateform.service.SysRoleService;
import com.google.personalhealthrecordingplateform.util.QueryInfo;
import com.google.personalhealthrecordingplateform.util.RedisUtils;
import com.google.personalhealthrecordingplateform.util.Result;
import com.google.personalhealthrecordingplateform.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/10/26 23:06
 */

@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {
    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;
    private final SysPermissionMapper sysPermissionMapper;
    private final RedisUtils redisUtils;

    @Autowired
    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper, SysMenuMapper sysMenuMapper, SysPermissionMapper sysPermissionMapper, RedisUtils redisUtils) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysMenuMapper = sysMenuMapper;
        this.sysPermissionMapper = sysPermissionMapper;
        this.redisUtils = redisUtils;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Result insert(SysRole sysRole) {
        SysRole role = sysRoleMapper.findRoleByLabel(sysRole.getLabel());
        if (null != role) {
            return Result.fail("该角色已经存在");
        }
        //增加角色信息的时候，要把redis里面的那个对应的用户数据缓存删除掉
        redisUtils.delete("java_sport:sys_user:" + SecurityUtils.getUsername());
        sysRoleMapper.insert(sysRole);


        //插入角色的同时，还要插入角色对应的菜单数据和权限数据
        //菜单包括父级菜单和子菜单，但是roleID-menuID
        //我的意思是系统管理下有用户管理，角色管理，权限管理，菜单管理，正常情况下，应该是这四个中的一个或者多个，而不是四个都有
        //如果没有父级菜单，直接就是子级菜单，那么这个角色的用户登录之后，那个父级菜单直接没有
        if (sysRole.getMenus().size() > 0) {
            List<SysMenu> list = sysRole.getMenus();
            list.forEach(item -> {
                sysRoleMapper.insertMenus(sysRole.getId(), item.getId());
                if (item.getChildren() != null) {
                    item.getChildren().forEach(item1 -> {
                        sysRoleMapper.insertMenus(sysRole.getId(), item1.getId());
                    });
                }
            });
        }
        //果上面的菜单没有选用户管理，但是我在权限这里勾选了增加用户，删除用户的功能，那这个角色还能进行增加用户和删除用户的功能吗？
        //即如果没有勾选用户管理，那么对用户操作的权限是不是都是灰色，即不能勾选   答：这个角色只是不能通过图形界面操作而已，但是能直接调用接口
        //如果我勾选了用户管理，但是没有勾选权限   答：没问题啊，因为进行操作的时候会去检验这个角色有该权限没有。
        if (sysRole.getPermissions().size() > 0) {
            List<SysPermission> list = sysRole.getPermissions();
            list.forEach(item -> {
                sysRoleMapper.insertPermissions(sysRole.getId(), item.getId());
            });
        }
        return Result.success("成功插入角色信息");
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void delete(Long roleId) {
        sysRoleMapper.delete(roleId);
        sysRoleMapper.deleteMenus(roleId);
        sysRoleMapper.deletePermissions(roleId);
        redisUtils.delete("java_sport:sys_user:" + SecurityUtils.getUsername());
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(SysRole sysRole) {
        //FIXME 当取消一个角色已有的权限信息的时候，前端传过来的sysRole有问题，导致不能成功修改角色的权限信息。
        //插入之前，必须保证role_permission表中不存在这一对数据,否则会发生记录重复。
        //数据库中会存在重复的记录原因，修改操作的时候
        //修改的时候，可能删除了某些记录，也可能增加了某些记录。
        sysRoleMapper.update(sysRole);
        //不仅仅要更新role信息，role对应的菜单和权限也会更改。
        sysRoleMapper.deletePermissions(sysRole.getId());
        if (sysRole.getPermissions() != null && sysRole.getPermissions().size() > 0) {
            sysRole.getPermissions().forEach(item -> sysRoleMapper.insertPermissions(sysRole.getId(), item.getId()));
        }
        sysRoleMapper.deleteMenus(sysRole.getId());
        if (sysRole.getMenus() != null && sysRole.getMenus().size() > 0) {
            sysRole.getMenus().forEach(item -> sysRoleMapper.insertMenus(sysRole.getId(), item.getId()));
        }
        //因为redis中存储了当前用户登录时显示在前端的所有信息，现在我们更新了数据库中的角色信息，因此redis中的数据不是最新的，需要进行删除
        log.info("删除redis键值对 key:" + SecurityUtils.getUsername());
        redisUtils.delete("java_sport:sys_user:" + SecurityUtils.getUsername());
        //前端更新完之后，还要从后台拿到最新数据啊，即调用了findPage()
    }

    @Override
    public List<SysRole> findPage(QueryInfo queryInfo) {
        List<SysRole> list = sysRoleMapper.findPage(queryInfo);
        //为每个Role加入菜单和权限
        list.forEach(item -> {
            item.setMenus(this.sysMenuMapper.findMenuByRoleID(item.getId()));
            item.setPermissions(this.sysPermissionMapper.findPermissionByRoleID(item.getId()));
        });
        return list;
    }
}

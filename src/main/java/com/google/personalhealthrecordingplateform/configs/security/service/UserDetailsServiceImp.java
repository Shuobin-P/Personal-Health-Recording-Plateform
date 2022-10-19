package com.google.personalhealthrecordingplateform.configs.security.service;

import com.google.personalhealthrecordingplateform.mapper.SysUserMapper;
import com.google.personalhealthrecordingplateform.model.SysMenu;
import com.google.personalhealthrecordingplateform.model.SysUser;
import com.google.personalhealthrecordingplateform.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("userDetailsServiceImp")
public class UserDetailsServiceImp implements UserDetailsService {
    private RedisUtils redisUtils;
    private SysUserMapper sysUserMapper;

    @Autowired
    public UserDetailsServiceImp(SysUserMapper sysUserMapper, RedisUtils redisUtils) {
        this.sysUserMapper = sysUserMapper;
        this.redisUtils = redisUtils;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //设置缓存，在缓存中查找用户信息，若有信息，则返回，若无信息，则把信息从数据库中取出，并存入缓存中
        //如何从Redis中拿到用户信息

        SysUser sysUser = sysUserMapper.findUserByUserName(username);
        if (sysUser == null) throw new UsernameNotFoundException("用户名不存在");
        log.info("sysUser UserName: " + sysUser.getUsername());
        if (sysUser.isAdmin()) {
            sysUser.setRoles(sysUserMapper.findRoles(null));
            sysUser.setPermissions(sysUserMapper.findPermissions(null));
            List<SysMenu> menus = sysUserMapper.findMenus(null);
            menus.forEach(item -> item.setChildren(sysUserMapper.findChildrenMenus(item.getId(), null)));
            sysUser.setMenus(menus);
        } else {
            sysUser.setRoles(sysUserMapper.findRoles(sysUser.getId()));
            sysUser.setPermissions(sysUserMapper.findPermissions(sysUser.getId()));
            List<SysMenu> menus = sysUserMapper.findMenus(sysUser.getId());
            menus.forEach(item -> item.setChildren(sysUserMapper.findChildrenMenus(item.getId(), sysUser.getId())));
            sysUser.setMenus(menus);
        }
        return sysUser;
    }
}

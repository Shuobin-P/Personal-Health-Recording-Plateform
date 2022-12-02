package com.google.personalhealthrecordingplateform.security.service;

import com.google.personalhealthrecordingplateform.mapper.SysUserMapper;
import com.google.personalhealthrecordingplateform.entity.SysMenu;
import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author W&F
 */
@Slf4j
@Service("userDetailsServiceImp")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RedisUtils redisUtils;
    private final SysUserMapper sysUserMapper;

    @Autowired
    public UserDetailsServiceImpl(SysUserMapper sysUserMapper, RedisUtils redisUtils) {
        this.sysUserMapper = sysUserMapper;
        this.redisUtils = redisUtils;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //设置缓存，在缓存中查找用户信息，若有信息，则返回，若无信息，则把信息从数据库中取出，并存入缓存中
        SysUser sysUser;
        //在缓存中
        if (redisUtils.hasKey(username)) {
            sysUser = (SysUser) redisUtils.get("java_sport:sys_user:" + username);
            redisUtils.setExpiration("java_sport:sys_user:" + username, 600);
        } else {
            //不在缓存中
            sysUser = sysUserMapper.findUserByUserName(username);
            if (sysUser == null) {
                throw new UsernameNotFoundException("用户名不存在");
            }
            log.info("sysUser UserName: " + sysUser.getUsername());
            Boolean isAdmin = sysUser.getAdmin();
            if (isAdmin != null && isAdmin == true) {
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
            redisUtils.add("java_sport:sys_user:" + username, sysUser);
            //缓存设置的时间应该为多长合适？因为是用户的账号，密码，邮件地址，头像，只要用户进行登录，这些东西就需要
            redisUtils.setExpiration("java_sport:sys_user:" + username, 600);
        }

        return sysUser;
    }
}

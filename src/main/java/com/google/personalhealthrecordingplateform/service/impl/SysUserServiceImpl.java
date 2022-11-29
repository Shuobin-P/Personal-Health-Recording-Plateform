package com.google.personalhealthrecordingplateform.service.impl;

import com.google.personalhealthrecordingplateform.entity.SysRole;
import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.mapper.SysUserMapper;
import com.google.personalhealthrecordingplateform.service.SysUserService;
import com.google.personalhealthrecordingplateform.util.*;
import com.google.personalhealthrecordingplateform.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author W&F
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    private final TokenUtils tokenUtils;
    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    @Autowired
    private RedisUtils redisUtils;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Autowired
    public SysUserServiceImpl(TokenUtils tokenUtils, PasswordEncoder passwordEncoder, SysUserMapper sysUserMapper, @Qualifier("userDetailsServiceImp") UserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
        this.passwordEncoder = passwordEncoder;
        this.sysUserMapper = sysUserMapper;
        this.userDetailsService = userDetailsService;
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Result insert(SysUser sysUser) {
        SysUser su = sysUserMapper.findUserByUserName(sysUser.getUsername());
        if (null != su) {
            return Result.fail("用户名已经存在！");
        }
        sysUser.setPassword(passwordEncoder.encode(MD5Utils.md5(sysUser.getPassword())));
        sysUserMapper.insert(sysUser);
        Iterator<SysRole> iterator = sysUser.getRoles().iterator();
        while (iterator.hasNext()) {
            sysUserMapper.insertRole(sysUser.getId(), iterator.next().getId());
        }
        return Result.success("成功插入用户信息");
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void delete(Long userID) {
        sysUserMapper.delete(userID);
        sysUserMapper.deleteRole(userID);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Result update(SysUser sysUser) {
        redisUtils.delete("java_sport:sys_user:" + sysUser.getUsername());
        sysUserMapper.deleteRole(sysUser.getId());
        List<SysRole> roles = sysUser.getRoles();
        if (roles != null) {
            Iterator<SysRole> iterator = roles.iterator();
            while (iterator.hasNext()) {
                sysUserMapper.insertRole(sysUser.getId(), iterator.next().getId());
            }
        }
        if (sysUser.getPassword() != null) {
            sysUser.setPassword(passwordEncoder.encode(MD5Utils.md5(sysUser.getPassword())));
        }
        sysUserMapper.update(sysUser);
        return Result.success("用户信息修改成功！");
    }

    @Override
    public Result findAll() {
        log.info("获取所有用户信息");
        return Result.success("查询成功", sysUserMapper.findAll());
    }

    @Override
    public List<SysUser> findPage(QueryInfo queryInfo) {
        List<SysUser> list = sysUserMapper.findPage(queryInfo);
        list.forEach(item -> {
            item.setName(item.getUsername());
            item.setRoles(sysUserMapper.findRoles(item.getId()));
            item.setPassword(null);
        });
        return list;
    }

    @Override
    public String findAvatar(Long id) {
        return sysUserMapper.findAvatar(id);
    }

    @Override
    public SysUser findUser(String email) {
        return sysUserMapper.findUserByEmail(email);
    }

    @Override
    public Result selectByID(Integer id) {
        return Result.success("找到一条数据", sysUserMapper.selectByID(3));
    }


    @Override
    public Result login(LoginVO loginVo) {
        log.info("开始登录");
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginVo.getUsername());
        if (userDetails == null || !passwordEncoder.matches(MD5Utils.md5(loginVo.getPassword()), userDetails.getPassword())) {
            return Result.fail("账号或者密码错误，请重新输入");
        }
        if (!userDetails.isEnabled()) {
            return Result.fail("该账户已被禁用");
        }
        Authentication usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
        log.info("在SecurityContextHolder中添加登录者信息");
        //这里完成了身份认证，而且在SecurityContext中加入了authentication对象。为什么后面经过jwt过滤器的时候，SecurityContext中没有authentication对象
        //答：因为当前请求是有A线程处理的，后面再次发送的请求是由B线程处理的，A线程中的SecurityContext和B线程的SecurityContext不相同
        //此时我们在A线程的SecurityContext中加入Authentication，但是B线程的SecurityContext.getAuthentication中确是为空。
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        log.info("根据登录信息表获取token");
        String token = tokenUtils.generateToken(userDetails);
        Map<String, String> map = new HashMap<>(2);
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        return Result.success("登录成功", map);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return sysUserMapper.findUserByUserName(username);
    }
}

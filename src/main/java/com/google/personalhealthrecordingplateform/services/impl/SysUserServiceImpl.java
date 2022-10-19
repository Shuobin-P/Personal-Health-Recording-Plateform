package com.google.personalhealthrecordingplateform.services.impl;

import com.google.personalhealthrecordingplateform.mapper.SysUserMapper;
import com.google.personalhealthrecordingplateform.services.SysUserService;
import com.google.personalhealthrecordingplateform.utils.MD5Utils;
import com.google.personalhealthrecordingplateform.utils.Result;
import com.google.personalhealthrecordingplateform.utils.TokenUtil;
import com.google.personalhealthrecordingplateform.vo.LoginVo;
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

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;


    @Autowired
    @Qualifier("userDetailsServiceImp")
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenUtil tokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public Result findAll() {
        log.info("获取所有用户信息");
        return Result.success("查询成功", sysUserMapper.findAll());
    }

    @Override
    public Result selectByID(Integer id) {
        return Result.success("找到一条数据", sysUserMapper.selectByID(3));
    }


    @Override
    public Result login(LoginVo loginVo) {
        log.info("开始登录");
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginVo.getUsername());
        if (userDetails == null || !passwordEncoder.matches(MD5Utils.md5(loginVo.getPassword()),userDetails.getPassword()))
            return Result.fail("账号或者密码错误，请重新输入");
        if (!userDetails.isEnabled()) return Result.fail("该账户已被禁用");
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
        String token = tokenUtil.generateToken(userDetails);
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

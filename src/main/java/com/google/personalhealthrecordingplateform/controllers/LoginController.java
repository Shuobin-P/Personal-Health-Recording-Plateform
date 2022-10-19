package com.google.personalhealthrecordingplateform.controllers;

import com.google.personalhealthrecordingplateform.services.SysUserService;
import com.google.personalhealthrecordingplateform.utils.Result;
import com.google.personalhealthrecordingplateform.utils.SecurityUtil;
import com.google.personalhealthrecordingplateform.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户使用接口")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "用户登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        log.info("进入登录接口");
        return sysUserService.login(loginVo);
    }

    @ApiOperation(value = "退出登录接口")
    @GetMapping("/logout")
    public Result logout() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return Result.success("成功退出登录");
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getLoginInfo")
    public Result getUserInfo() {
        return Result.success("查询用户信息", SecurityUtil.getUserInfo());
    }
}

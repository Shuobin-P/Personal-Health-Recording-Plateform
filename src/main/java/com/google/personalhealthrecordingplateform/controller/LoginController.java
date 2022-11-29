package com.google.personalhealthrecordingplateform.controller;

import com.google.personalhealthrecordingplateform.service.SysUserService;
import com.google.personalhealthrecordingplateform.util.Result;
import com.google.personalhealthrecordingplateform.util.SecurityUtils;
import com.google.personalhealthrecordingplateform.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author W&F
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户使用接口")
public class LoginController {

    private final SysUserService sysUserService;

    @Autowired
    public LoginController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @ApiOperation(value = "用户登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVO loginVo) {
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
    @GetMapping("/getInfo")
    public Result getUserInfo() {
        return Result.success("查询用户信息", SecurityUtils.getUserInfo());
    }
}

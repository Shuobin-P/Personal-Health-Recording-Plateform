package com.google.personalhealthrecordingplateform.controller;

import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.service.MiniUserService;
import com.google.personalhealthrecordingplateform.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/12/21 16:34
 */
@Slf4j
@RestController
@RequestMapping("/mini")
@Api(tags = "小程序相关接口")
public class MiniController {
    private final MiniUserService miniUserService;

    @Autowired
    public MiniController(MiniUserService miniUserService) {
        this.miniUserService = miniUserService;
    }

    @ApiOperation(value = "微信小程序登录接口")
    @GetMapping("/login")
    public Result login(@RequestParam String code) throws URISyntaxException, IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        log.info("进入微信小程序登录接口");
        log.info(code);
        return miniUserService.login(code);
    }

    @GetMapping("/wxrun")
    public Result getSteps(@RequestParam String encryptedData, String iv) {
        //TODO 包含用户过去30天的步数
        //那为什么这里只返回一条数据。
        log.info("从微信获得加密数据：" + encryptedData);
        return miniUserService.getSteps(encryptedData, iv);
    }

    @ApiOperation(value = "微信小程序退出登录接口")
    @GetMapping("/logout")
    public Result logout() {
        log.info("进入微信小程序退出登录接口");
        return null;
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping("/update/info")
    public Result updateInfo(@RequestBody SysUser sysUser) {
        return miniUserService.updateInfoByOpenId(sysUser);
    }

}

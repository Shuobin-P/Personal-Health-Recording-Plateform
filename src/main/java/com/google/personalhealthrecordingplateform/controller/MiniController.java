package com.google.personalhealthrecordingplateform.controller;

import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.service.MiniUserService;
import com.google.personalhealthrecordingplateform.util.MiniEncryptedData;
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

    @ApiOperation(value = "返回今天的步数")
    @PostMapping("/wxrun")
    public Result getTodaySteps(@RequestBody MiniEncryptedData encryptedData) {
        //获得30天的数据，然后只返回今天的步数
        log.info("从微信获得加密数据：" + encryptedData.getEncryptedData());
        log.info("Authorization: " + encryptedData.getOpenid());
        return miniUserService.getTodaySteps(encryptedData.getEncryptedData(), encryptedData.getIv(), encryptedData.getOpenid());
    }

    @GetMapping("/step/report")
    public Result getRecentFourWeeksSteps(@RequestHeader String Authorization) {
        //统计最近四周的步数数据，今天这一周算第一周，又叫week1
        //星期一的数据：命名为0
        //星期二的数据：命名为1
        //上一周的数据：命名为week2
        return miniUserService.getRecentFourWeeksSteps(Authorization);
    }

    @ApiOperation(value = "微信小程序退出登录接口")
    @GetMapping("/logout")
    public Result logout(@RequestHeader String Authorization) {
        log.info("进入微信小程序退出登录接口");
        return miniUserService.logout(Authorization);
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping("/update/info")
    public Result updateInfo(@RequestBody SysUser sysUser) {
        return miniUserService.updateInfoByOpenId(sysUser);
    }

}

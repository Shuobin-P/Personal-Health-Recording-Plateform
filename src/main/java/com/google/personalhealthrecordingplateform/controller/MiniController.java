package com.google.personalhealthrecordingplateform.controller;

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
    public Result getRunStep(@RequestParam String encryptedData, String iv) {
        //同样的前端（我更改以后的小程序），后端不一样，而且up的后端这个方法的post并没有实现，
        //所以不是这个方法的原因
        return Result.success("成功查询到微信步数", 456);
    }

    @ApiOperation(value = "微信小程序退出登录接口")
    @GetMapping("/logout")
    public Result logout() {
        log.info("进入微信小程序退出登录接口");
        return null;
    }
}

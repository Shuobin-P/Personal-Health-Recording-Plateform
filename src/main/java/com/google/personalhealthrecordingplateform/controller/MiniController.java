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
    public Result getWeRunStep(@RequestParam String encryptedData, String iv) {
        //TODO 用up原来的后端就可以正常运行，但是使用我的后端代码却不能正常运行，根据前端调用的接口，应该是因为这个接口没写
        //前端发送加密后的数据过来，后端使用session_key解密返回给小程序。
        //TODO 返回的数据格式是什么样子的？这个接口具体是干什么的？
        //JWT过滤器那边依然会处理JWT，查找Authentication
        return Result.success("成功查询微信步数", 654);
    }

    @ApiOperation(value = "微信小程序退出登录接口")
    @GetMapping("/logout")
    public Result logout() {
        log.info("进入微信小程序退出登录接口");
        return null;
    }
}

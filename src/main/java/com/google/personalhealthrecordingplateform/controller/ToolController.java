package com.google.personalhealthrecordingplateform.controller;

import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.service.SysUserService;
import com.google.personalhealthrecordingplateform.util.*;
import com.google.personalhealthrecordingplateform.vo.MailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author W&F
 * @version 1.0
 * @date 2022/11/18 21:42
 */
@Slf4j
@Api(tags = "工具接口")
@RestController
@RequestMapping("/tool")
public class ToolController {
    private final QiniuUtils qiniuUtils;
    private final EmailUtils emailUtils;
    private final SysUserService sysUserService;

    @Autowired
    public ToolController(QiniuUtils qiniuUtils, EmailUtils emailUtils, SysUserService sysUserService) {
        this.qiniuUtils = qiniuUtils;
        this.emailUtils = emailUtils;
        this.sysUserService = sysUserService;
    }

    @ApiOperation(value = "七牛云文件上传")
    @PostMapping("/upload")
    public Result uploadAvatar(@RequestBody MultipartFile file) throws IOException {
        String url = qiniuUtils.upload((FileInputStream) file.getInputStream(),
                StringUtils.getRandomImgName(file.getOriginalFilename()));

        return Result.success("成功上传头像", url);
    }

    @ApiOperation(value = "忘记密码")
    @PostMapping("/forget/password")
    public Result forgetPassword(@RequestBody MailVO mailVo) throws MessagingException {
        log.info("进入忘记密码方法");
        SysUser sysUser = sysUserService.findUser(mailVo.receivers[0]);
        if (sysUser == null) {
            return Result.fail("该邮箱未注册过账号");
        } else {
            String pwd = StringUtils.generateRandomPassword(10);
            sysUser.setPassword(pwd);
            sysUserService.update(sysUser);
            mailVo.setSubject("个人运动管理平台");
            mailVo.setContent("您的新密码:" + pwd);
            mailVo.setHtml(false);
            emailUtils.sendMail(mailVo);
            return Result.success("成功修改密码");
        }

    }

}

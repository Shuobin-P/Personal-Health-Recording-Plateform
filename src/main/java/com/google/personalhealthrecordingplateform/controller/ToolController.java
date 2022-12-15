package com.google.personalhealthrecordingplateform.controller;

import com.google.personalhealthrecordingplateform.entity.SysUser;
import com.google.personalhealthrecordingplateform.service.SmsService;
import com.google.personalhealthrecordingplateform.service.SysUserService;
import com.google.personalhealthrecordingplateform.util.EmailUtils;
import com.google.personalhealthrecordingplateform.util.QiniuUtils;
import com.google.personalhealthrecordingplateform.util.Result;
import com.google.personalhealthrecordingplateform.util.StringUtils;
import com.google.personalhealthrecordingplateform.vo.MailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    private final SmsService smsService;
    private final SysUserService sysUserService;

    @Autowired
    public ToolController(QiniuUtils qiniuUtils, EmailUtils emailUtils, SmsService smsService, SysUserService sysUserService) {
        this.qiniuUtils = qiniuUtils;
        this.emailUtils = emailUtils;
        this.smsService = smsService;
        this.sysUserService = sysUserService;
    }

    @ApiOperation(value = "七牛云文件上传")
    @PostMapping("/upload")
    public Result uploadAvatar(@RequestBody MultipartFile file) throws IOException {
        String url = qiniuUtils.upload((FileInputStream) file.getInputStream(), file.getOriginalFilename());
        return Result.success("成功上传头像", url);
    }

    @ApiOperation(value = "忘记密码")
    @PostMapping("/forget/password")
    public Result forgetPassword(@RequestBody MailVO mailVo) throws MessagingException {
        log.info("进入忘记密码方法");
        SysUser sysUser = sysUserService.findUserByEmail(mailVo.receivers[0]);
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

    @ApiOperation(value = "短信验证码")
    @PostMapping("/sms")
    public Result sendSms(@RequestParam("phoneNumber") String phoneNumber) {
        // 先检查数据库中是否存在该电话号码，如果不存在则告知用户该电话号码不存在
        // 前端把电话号码发过来，后端生成验证码，同时要运营商向指定电话号码发送包含验证码的短信，把电话号码，验证码 存储到哪里？同时验证码还要设置存在期限。同时向电话号主人发送短信
        // 等前端输入验证码，发送post到login接口，对loginVO进行判断，如果type = 2，则与redis存储的电话号码，验证码进行比对，
        // 如果正确，判断验证码是否过期
        //          如果过期了，告知用户验证码已失效
        //          否则，返回一个一个token，并跳转到用户首页。
        // 如果错误，告知用户验证码错误。
        SysUser sysUser = sysUserService.findUserByPhoneNumber(phoneNumber);
        if (sysUser == null) {
            return Result.fail("该电话号码没有注册过账号");
        }
        smsService.sendVerificationCode(phoneNumber);
        return Result.success("已发送包含验证码的短信，注意查收");
    }
}

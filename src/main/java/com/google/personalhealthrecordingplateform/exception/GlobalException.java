package com.google.personalhealthrecordingplateform.exception;

import com.google.personalhealthrecordingplateform.util.Result;
import com.qiniu.common.QiniuException;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;

/**
 * 后端报错了，前端tm的也要显示？
 * 登录接口测试+全局异常处理
 *
 * @author W&F
 */

@Slf4j
@RestControllerAdvice
public class GlobalException {
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = RuntimeException.class)
//    public Result exception(RuntimeException e) {
//        log.error("系统运行时异常-->{}", e.getMessage());
//        return Result.fail(e.getMessage());
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public Result exception(UsernameNotFoundException e) {
        log.error("用户名或密码错误-->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = QiniuException.class)
    public Result exception(QiniuException e) {
        log.error("七牛云操作异常-->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = MessagingException.class)
    public Result exception(MessagingException e) {
        log.error("邮件发送异常-->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = TencentCloudSDKException.class)
    public Result exception(TencentCloudSDKException e) {
        log.error("短信发送异常-->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

}

package com.google.personalhealthrecordingplateform.exceptions;

import com.google.personalhealthrecordingplateform.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 后端报错了，前端tm的也要显示？
 * 登录接口测试+全局异常处理
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
}

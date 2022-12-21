package com.google.personalhealthrecordingplateform.exception;

import com.google.personalhealthrecordingplateform.util.Result;
import com.qiniu.common.QiniuException;
import javassist.tools.web.BadHttpRequest;
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
        log.error("邮件操作异常-->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = FoodTypeException.class)
    public Result exception(FoodTypeException e) {
        log.error("食物分类异常-->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = SportNewsException.class)
    public Result exception(SportNewsException e) {
        log.error("食物分类异常-->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = GoodsException.class)
    public Result exception(GoodsException e) {
        log.error("商品异常-->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadHttpRequest.class)
    public Result exception(BadHttpRequest e) {
        log.error("商品异常-->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = SportException.class)
    public Result exception(SportException e) {
        log.error("运动项目异常-->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e) {
        log.error("异常-->{}", e.getMessage());
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }
}

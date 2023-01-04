package com.google.personalhealthrecordingplateform.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.personalhealthrecordingplateform.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author W&F
 * 用户未完成身份验证，可以把用户重定向到登录页面 或者 发送一个wWWW-Authenticate头
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        //是不是因为登录的时候，创建的那个Authentication不够完整，所以才进入到这个方法，说Full authentication is required to access this resource
        log.info("抛出AuthenticationException：" + authException.toString());

        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(Result.fail("您未登录")));
        writer.flush();
        writer.close();
    }
}

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

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.info("进入JwtAuthenticationEntryPoint方法");
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        //若返回状态码是401，前端不会展示下面的信息
        writer.write(new ObjectMapper().writeValueAsString(Result.fail("您未登录")));
        writer.flush();
        writer.close();
    }
}

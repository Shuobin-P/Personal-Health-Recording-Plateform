package com.google.personalhealthrecordingplateform.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.personalhealthrecordingplateform.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(Result.fail("请登录后重试!")));
        writer.flush();
        writer.close();
    }
}
